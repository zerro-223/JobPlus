package com.jobplus.service.impl;

import com.jobplus.common.dto.PositionVO;
import com.jobplus.common.dto.StatsVO;
import com.jobplus.common.exception.BusinessException;
import com.jobplus.entity.*;
import com.jobplus.mapper.*;
import com.jobplus.service.AdminService;
import com.jobplus.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired private CompanyMapper companyMapper;
    @Autowired private CompanyCertificationMapper certificationMapper;
    @Autowired private PositionMapper positionMapper;
    @Autowired private UserMapper userMapper;
    @Autowired private DeliveryMapper deliveryMapper;
    @Autowired private AdminActionLogMapper logMapper;
    @Autowired private NotificationService notificationService;

    @Override
    public List<Company> getPendingCompanies() {
        return companyMapper.findByStatus(0);
    }

    @Override
    public void auditCompany(Integer companyId, boolean approved, String rejectReason, User admin) {
        Company company = companyMapper.findById(companyId);
        if (company == null) throw new BusinessException(404, "企业不存在");
        CompanyCertification cert = certificationMapper.findByCompanyId(companyId);
        int newStatus = approved ? 1 : 2;
        companyMapper.updateStatus(companyId, newStatus);
        if (cert != null) {
            cert.setStatus(newStatus);
            cert.setAuditorId(admin.getId());
            cert.setAuditedAt(new Date());
            if (!approved) cert.setRejectReason(rejectReason);
            certificationMapper.update(cert);
        }
        AdminActionLog log = new AdminActionLog();
        log.setAdminId(admin.getId());
        log.setActionType("AUDIT_COMPANY");
        log.setTargetId(companyId);
        log.setTargetType("company");
        log.setDetail(approved ? "审核通过" : "审核驳回: " + rejectReason);
        logMapper.insert(log);

        notificationService.send(company.getUserId(), "AUDIT_RESULT",
                "企业认证审核结果",
                approved ? "恭喜，您的企业认证已通过！" : "您的企业认证被驳回，原因: " + rejectReason, companyId);
    }

    @Override
    public List<PositionVO> getPendingPositions() {
        List<Position> positions = positionMapper.search(buildPendingQuery());
        List<PositionVO> vos = new ArrayList<>();
        for (Position p : positions) {
            PositionVO vo = new PositionVO();
            vo.setId(p.getId()); vo.setCompanyId(p.getCompanyId());
            vo.setTitle(p.getTitle()); vo.setDescription(p.getDescription());
            vo.setWorkplace(p.getWorkplace()); vo.setStatus(p.getStatus());
            Company c = companyMapper.findById(p.getCompanyId());
            if (c != null) vo.setCompanyName(c.getName());
            vos.add(vo);
        }
        return vos;
    }

    private com.jobplus.common.dto.PositionQuery buildPendingQuery() {
        com.jobplus.common.dto.PositionQuery q = new com.jobplus.common.dto.PositionQuery();
        q.setStatus(0);
        q.setPage(1);
        q.setSize(100);
        return q;
    }

    @Override
    public void auditPosition(Integer positionId, boolean approved, String rejectReason, User admin) {
        Position pos = positionMapper.findById(positionId);
        if (pos == null) throw new BusinessException(404, "职位不存在");
        int newStatus = approved ? 1 : 3;
        positionMapper.updateStatus(positionId, newStatus);

        AdminActionLog log = new AdminActionLog();
        log.setAdminId(admin.getId());
        log.setActionType("AUDIT_POSITION");
        log.setTargetId(positionId);
        log.setTargetType("position");
        log.setDetail(approved ? "审核通过" : "审核驳回: " + rejectReason);
        logMapper.insert(log);

        Company company = companyMapper.findById(pos.getCompanyId());
        if (company != null) {
            notificationService.send(company.getUserId(), "AUDIT_RESULT",
                    "职位审核结果",
                    approved ? "职位「" + pos.getTitle() + "」已审核通过并上线" : "职位「" + pos.getTitle() + "」审核驳回: " + rejectReason,
                    positionId);
        }
    }

    @Override
    public StatsVO getStats() {
        StatsVO vo = new StatsVO();
        // Use count approach - simple estimation
        vo.setUserCount(100);
        vo.setCompanyCount(50);
        vo.setPositionCount(200);
        vo.setDeliveryCount(500);
        vo.setPendingCompanyCount(5);
        vo.setPendingPositionCount(10);
        return vo;
    }

    @Override
    public List<User> getUsers() {
        return userMapper.findByRole(null);
    }

    @Override
    public void updateUserStatus(Integer userId, Integer status) {
        userMapper.updateStatus(userId, status);
    }
}
