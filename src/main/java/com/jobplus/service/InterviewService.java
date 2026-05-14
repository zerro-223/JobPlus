package com.jobplus.service;

import com.jobplus.entity.*;

import java.util.List;

public interface InterviewService {
    Interview createInterview(Interview interview, User user);
    List<Interview> getMyInterviews(User user);
    void updateStatus(Integer id, Integer status, User user);
}
