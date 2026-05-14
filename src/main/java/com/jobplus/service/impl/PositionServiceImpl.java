package com.jobplus.service.impl;

import com.jobplus.common.dto.*;
import com.jobplus.common.exception.BusinessException;
import com.jobplus.entity.*;
import com.jobplus.mapper.*;
import com.jobplus.service.NotificationService;
import com.jobplus.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PositionServiceImpl implements PositionService {

    @Autowired private PositionMapper positionMapper;
    @Autowired private CompanyMapper companyMapper;
    @Autowired private FavoriteMapper favoriteMapper;
    @Autowired private AdminActionLogMapper adminActionLogMapper;
    @Autowired private NotificationService notificationService;

    @Override
    public PageResult<PositionVO> search(PositionQuery query) {
        List<Position> positions = positionMapper.search(query);
        long total = positionMapper.count(query);
        List<PositionVO> voList = new ArrayList<>();
        for (Position p : positions) {
            voList.add(toVO(p));
        }
        int page = query.getPage();
        int size = query.getSize();
        return new PageResult<>(voList, page, size, total);
    }

    @Override
    public PositionVO getDetail(Integer id, Integer userId) {
        Position position = positionMapper.findById(id);
        if (position == null) throw new BusinessException(404, "职位不存在");
        positionMapper.incrementViewCount(id);
        PositionVO vo = toVO(position);
        if (userId != null) {
            vo.setIsFavorited(favoriteMapper.exists(userId, id));
        }
        return vo;
    }

    @Override
    public PositionVO create(PositionCreateReq req, User user) {
        Company company = companyMapper.findByUserId(user.getId());
        if (company == null) throw new BusinessException(400, "请先创建企业信息");
        if (company.getStatus() != 1) throw new BusinessException(400, "企业未认证，无法发布职位");

        Position position = new Position();
        position.setCompanyId(company.getId());
        position.setCategoryId(req.getCategoryId());
        position.setTitle(req.getTitle());
        position.setDescription(req.getDescription());
        position.setRequirement(req.getRequirement());
        position.setSalaryMin(req.getSalaryMin());
        position.setSalaryMax(req.getSalaryMax());
        position.setEducation(req.getEducation());
        position.setExperience(req.getExperience());
        position.setWorkplace(req.getWorkplace());
        position.setTags(req.getTags());
        position.setStatus(0);
        position.setViewCount(0);
        position.setDeliveryCount(0);
        positionMapper.insert(position);
        return toVO(position);
    }

    @Override
    public void update(PositionUpdateReq req, User user) {
        Position position = positionMapper.findById(req.getId());
        if (position == null) throw new BusinessException(404, "职位不存在");
        Company company = companyMapper.findByUserId(user.getId());
        if (company == null || !company.getId().equals(position.getCompanyId()))
            throw new BusinessException(403, "无权修改");

        position.setTitle(req.getTitle());
        position.setDescription(req.getDescription());
        position.setRequirement(req.getRequirement());
        position.setCategoryId(req.getCategoryId());
        position.setSalaryMin(req.getSalaryMin());
        position.setSalaryMax(req.getSalaryMax());
        position.setEducation(req.getEducation());
        position.setExperience(req.getExperience());
        position.setWorkplace(req.getWorkplace());
        position.setTags(req.getTags());
        position.setStatus(0);
        positionMapper.update(position);
    }

    @Override
    public void updateStatus(Integer id, Integer status, User user) {
        Position position = positionMapper.findById(id);
        if (position == null) throw new BusinessException(404, "职位不存在");
        Company company = companyMapper.findByUserId(user.getId());
        if (company == null || !company.getId().equals(position.getCompanyId()))
            throw new BusinessException(403, "无权操作");
        positionMapper.updateStatus(id, status);
    }

    @Override
    public void audit(Integer id, Integer adminId, boolean approved, String reason) {
        Position position = positionMapper.findById(id);
        if (position == null) throw new BusinessException(404, "职位不存在");
        int newStatus = approved ? 1 : 3;
        positionMapper.updateStatus(id, newStatus);

        AdminActionLog log = new AdminActionLog();
        log.setAdminId(adminId);
        log.setActionType(approved ? "AUDIT_POSITION_APPROVE" : "AUDIT_POSITION_REJECT");
        log.setTargetId(id);
        log.setTargetType("position");
        log.setDetail(reason);
        adminActionLogMapper.insert(log);

        Company company = companyMapper.findById(position.getCompanyId());
        if (company != null) {
            notificationService.send(company.getUserId(), "AUDIT_RESULT",
                    approved ? "职位审核通过" : "职位审核驳回",
                    approved ? "您的职位「" + position.getTitle() + "」已通过审核"
                             : "您的职位「" + position.getTitle() + "」未通过审核，原因：" + reason,
                    id);
        }
    }

    @Override
    public List<PositionVO> getCompanyPositions(Integer companyId) {
        List<Position> positions = positionMapper.findByCompanyId(companyId);
        List<PositionVO> vos = new ArrayList<>();
        for (Position p : positions) vos.add(toVO(p));
        return vos;
    }

    private PositionVO toVO(Position position) {
        PositionVO vo = new PositionVO();
        vo.setId(position.getId());
        vo.setCompanyId(position.getCompanyId());
        vo.setCategoryId(position.getCategoryId());
        vo.setTitle(position.getTitle());
        vo.setDescription(position.getDescription());
        vo.setRequirement(position.getRequirement());
        vo.setSalaryMin(position.getSalaryMin());
        vo.setSalaryMax(position.getSalaryMax());
        vo.setEducation(position.getEducation());
        vo.setExperience(position.getExperience());
        vo.setWorkplace(position.getWorkplace());
        vo.setTags(position.getTags());
        vo.setStatus(position.getStatus());
        vo.setViewCount(position.getViewCount());
        vo.setDeliveryCount(position.getDeliveryCount());
        vo.setExpireAt(position.getExpireAt());
        vo.setCreatedAt(position.getCreatedAt());
        Company company = companyMapper.findById(position.getCompanyId());
        if (company != null) {
            vo.setCompanyName(company.getName());
            vo.setCompanyLogo(company.getLogoUrl());
        }
        return vo;
    }
}
