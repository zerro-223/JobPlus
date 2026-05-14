package com.jobplus.service;

import com.jobplus.common.dto.DeliveryVO;
import com.jobplus.entity.*;

import java.util.List;

public interface DeliveryService {
    Delivery deliver(Integer userId, Integer positionId, Integer resumeId);
    List<DeliveryVO> getMyDeliveries(Integer userId);
    DeliveryVO getDeliveryDetail(Integer id);
    void updateStatus(Integer id, Integer status, User user);
}
