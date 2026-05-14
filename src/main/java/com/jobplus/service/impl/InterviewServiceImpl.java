package com.jobplus.service.impl;

import com.jobplus.common.exception.BusinessException;
import com.jobplus.entity.*;
import com.jobplus.mapper.*;
import com.jobplus.service.InterviewService;
import com.jobplus.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InterviewServiceImpl implements InterviewService {
    @Autowired private InterviewMapper interviewMapper;
    @Autowired private DeliveryMapper deliveryMapper;
    @Autowired private PositionMapper positionMapper;
    @Autowired private CompanyMapper companyMapper;
    @Autowired private NotificationService notificationService;

    @Override
    public Interview createInterview(Interview interview, User user) {
        Company company = companyMapper.findByUserId(user.getId());
        if (company == null) throw new BusinessException(400, "企业信息不存在");
        Delivery delivery = deliveryMapper.findById(interview.getDeliveryId());
        if (delivery == null) throw new BusinessException(404, "投递记录不存在");
        Position pos = positionMapper.findById(delivery.getPositionId());
        if (!pos.getCompanyId().equals(company.getId()))
            throw new BusinessException(403, "无权操作");

        interview.setCompanyId(company.getId());
        interview.setUserId(delivery.getUserId());
        interview.setStatus(0);
        interviewMapper.insert(interview);

        deliveryMapper.updateStatus(delivery.getId(), 2);

        notificationService.send(delivery.getUserId(), "INTERVIEW",
                "收到面试邀请",
                company.getName() + " 邀请您面试: " + pos.getTitle(), interview.getId());
        return interview;
    }

    @Override
    public List<Interview> getMyInterviews(User user) {
        if (user.getRole() == 2) {
            Company company = companyMapper.findByUserId(user.getId());
            return company != null ? interviewMapper.findByCompanyId(company.getId()) : null;
        }
        return interviewMapper.findByUserId(user.getId());
    }

    @Override
    public void updateStatus(Integer id, Integer status, User user) {
        Interview interview = interviewMapper.findById(id);
        if (interview == null) throw new BusinessException(404, "面试记录不存在");
        if (!interview.getUserId().equals(user.getId()))
            throw new BusinessException(403, "无权操作");
        interviewMapper.updateStatus(id, status);
    }
}
