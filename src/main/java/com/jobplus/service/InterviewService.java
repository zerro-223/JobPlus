package com.jobplus.service;

import com.jobplus.entity.Interview;

import java.util.List;

/**
 * 面试服务接口
 */
public interface InterviewService {

    Interview createInterview(Integer userId, Integer deliveryId, Interview interview);

    List<Interview> getMyInterviews(Integer userId);

    void updateStatus(Integer id, Integer status, Integer userId);
}
