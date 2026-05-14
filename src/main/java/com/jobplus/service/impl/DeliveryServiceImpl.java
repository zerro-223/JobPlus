package com.jobplus.service.impl;

import com.jobplus.common.dto.DeliveryVO;
import com.jobplus.common.exception.BusinessException;
import com.jobplus.entity.*;
import com.jobplus.mapper.*;
import com.jobplus.service.DeliveryService;
import com.jobplus.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DeliveryServiceImpl implements DeliveryService {
    @Autowired private DeliveryMapper deliveryMapper;
    @Autowired private PositionMapper positionMapper;
    @Autowired private ResumeMapper resumeMapper;
    @Autowired private CompanyMapper companyMapper;
    @Autowired private UserMapper userMapper;
    @Autowired private NotificationService notificationService;

    @Override
    public Delivery deliver(Integer userId, Integer positionId, Integer resumeId) {
        User user = userMapper.findById(userId);
        if (user == null || user.getRole() != 1)
            throw new BusinessException(403, "仅求职者可投递职位");

        Position position = positionMapper.findById(positionId);
        if (position == null || position.getStatus() != 1)
            throw new BusinessException(400, "职位不存在或已关闭");

        Delivery existing = deliveryMapper.findByUserAndPosition(userId, positionId);
        if (existing != null)
            throw new BusinessException(400, "您已投递该职位，请勿重复投递");

        Resume resume = resumeMapper.findById(resumeId);
        if (resume == null || !resume.getUserId().equals(userId))
            throw new BusinessException(400, "简历不存在或无权使用");

        Delivery delivery = new Delivery();
        delivery.setUserId(userId);
        delivery.setPositionId(positionId);
        delivery.setResumeId(resumeId);
        delivery.setStatus(0);
        delivery.setCompanyRead(false);
        deliveryMapper.insert(delivery);

        positionMapper.incrementDeliveryCount(positionId);

        Company company = companyMapper.findById(position.getCompanyId());
        notificationService.send(company.getUserId(), "NEW_DELIVERY",
                "收到新的职位投递",
                (user.getNickname() != null ? user.getNickname() : "求职者") + " 投递了职位: " + position.getTitle(),
                delivery.getId());

        return delivery;
    }

    @Override
    public List<DeliveryVO> getMyDeliveries(Integer userId) {
        List<Delivery> deliveries = deliveryMapper.findByUserId(userId);
        List<DeliveryVO> vos = new ArrayList<>();
        for (Delivery d : deliveries) {
            DeliveryVO vo = buildVO(d);
            vos.add(vo);
        }
        return vos;
    }

    @Override
    public DeliveryVO getDeliveryDetail(Integer id) {
        Delivery d = deliveryMapper.findById(id);
        if (d == null) throw new BusinessException(404, "投递记录不存在");
        return buildVO(d);
    }

    @Override
    public void updateStatus(Integer id, Integer status, User user) {
        Delivery d = deliveryMapper.findById(id);
        if (d == null) throw new BusinessException(404, "投递记录不存在");
        Position pos = positionMapper.findById(d.getPositionId());
        Company company = companyMapper.findById(pos.getCompanyId());
        if (!company.getUserId().equals(user.getId()))
            throw new BusinessException(403, "无权操作");

        if (status == 1 && !d.getCompanyRead()) {
            deliveryMapper.markRead(id);
        }
        deliveryMapper.updateStatus(id, status);

        User candidate = userMapper.findById(d.getUserId());
        String statusMsg = status == 1 ? "被查看" : status == 2 ? "邀面试" : status == 3 ? "不合适" : "状态变更";
        notificationService.send(candidate.getId(), "DELIVERY_STATUS",
                "投递状态更新",
                "您在 " + pos.getTitle() + " 的投递状态已更新为: " + statusMsg, d.getId());
    }

    private DeliveryVO buildVO(Delivery d) {
        DeliveryVO vo = new DeliveryVO();
        vo.setId(d.getId()); vo.setUserId(d.getUserId());
        vo.setPositionId(d.getPositionId()); vo.setResumeId(d.getResumeId());
        vo.setStatus(d.getStatus()); vo.setCompanyRead(d.getCompanyRead());
        vo.setReadAt(d.getReadAt()); vo.setCreatedAt(d.getCreatedAt());
        Position pos = positionMapper.findById(d.getPositionId());
        if (pos != null) {
            vo.setPositionTitle(pos.getTitle());
            Company c = companyMapper.findById(pos.getCompanyId());
            if (c != null) vo.setCompanyName(c.getName());
        }
        return vo;
    }
}
