package com.jobplus.service;

import com.jobplus.common.dto.UserRegisterReq;
import com.jobplus.entity.User;

public interface UserService {
    User register(UserRegisterReq req);
    User login(String email, String password);
    User getProfile(Integer userId);
    void updateProfile(User user);
    void updatePassword(Integer userId, String oldPwd, String newPwd);
}
