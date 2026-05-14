package com.jobplus.service.impl;

import com.jobplus.common.dto.DeliveryVO;
import com.jobplus.common.dto.Result;
import com.jobplus.common.exception.BusinessException;
import com.jobplus.entity.*;
import com.jobplus.mapper.*;
import com.jobplus.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
    @Autowired private CompanyMapper companyMapper;
    @Autowired private CompanyCertificationMapper certificationMapper;
    @Autowired private PositionMapper positionMapper;
    @Autowired private DeliveryMapper deliveryMapper;
    @Autowired private ResumeMapper resumeMapper;
    @Autowired private UserMapper userMapper;
    @Autowired private NotificationService notificationService;

    @Override
    public Company getProfile(Integer userId) {
        return companyMapper.findByUserId(userId);
    }

    @Override
    public void updateProfile(Company company, User user) {
        Company existing = companyMapper.findByUserId(user.getId());
        if (existing == null) throw new BusinessException(404, "企业信息不存在");
        company.setId(existing.getId());
        company.setUserId(user.getId());
        company.setStatus(existing.getStatus());
        companyMapper.update(company);
    }

    @Override
    public void submitCertification(CompanyCertification cert, User user) {
        Company company = companyMapper.findByUserId(user.getId());
        if (company == null) throw new BusinessException(400, "请先完善企业信息");
        CompanyCertification existing = certificationMapper.findByCompanyId(company.getId());
        if (existing != null) {
            cert.setId(existing.getId());
            cert.setCompanyId(company.getId());
            cert.setStatus(0);
            certificationMapper.update(cert);
        } else {
            cert.setCompanyId(company.getId());
            cert.setStatus(0);
            certificationMapper.insert(cert);
        }
        company.setStatus(0);
        companyMapper.updateStatus(company.getId(), 0);
    }

    @Override
    public Company getPublicInfo(Integer companyId) {
        return companyMapper.findById(companyId);
    }

    @Override
    public List<Position> getPositions(Integer companyId) {
        return positionMapper.findByCompanyId(companyId);
    }

    @Override
    public List<DeliveryVO> getDeliveries(User user) {
        Company company = companyMapper.findByUserId(user.getId());
        if (company == null) throw new BusinessException(400, "请先完善企业信息");
        List<Delivery> deliveries = deliveryMapper.findByCompanyId(company.getId());
        List<DeliveryVO> vos = new ArrayList<>();
        for (Delivery d : deliveries) {
            DeliveryVO vo = new DeliveryVO();
            copyDelivery(d, vo);
            Position pos = positionMapper.findById(d.getPositionId());
            if (pos != null) vo.setPositionTitle(pos.getTitle());
            Resume resume = resumeMapper.findById(d.getResumeId());
            if (resume != null) vo.setCandidateName(resume.getRealName());
            vos.add(vo);
        }
        return vos;
    }

    private void copyDelivery(Delivery src, DeliveryVO dst) {
        dst.setId(src.getId()); dst.setUserId(src.getUserId());
        dst.setPositionId(src.getPositionId()); dst.setResumeId(src.getResumeId());
        dst.setStatus(src.getStatus()); dst.setCompanyRead(src.getCompanyRead());
        dst.setReadAt(src.getReadAt()); dst.setCreatedAt(src.getCreatedAt());
    }
}
