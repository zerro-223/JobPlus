package com.jobplus.service;

import com.jobplus.entity.Delivery;
import com.jobplus.entity.vo.DeliveryVO;

import java.util.List;

/**
 * 投递服务接口
 */
public interface DeliveryService {

    Delivery deliver(Integer userId, Integer positionId, Integer resumeId);

    List<DeliveryVO> getMyDeliveries(Integer userId);

    DeliveryVO getDeliveryDetail(Integer id);

    void updateStatus(Integer id, Integer status, Integer userId);
}
