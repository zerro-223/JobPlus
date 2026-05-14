package com.jobplus.service;

import com.jobplus.entity.User;
import com.jobplus.entity.vo.UserVO;

/**
 * 用户服务接口
 */
public interface UserService {

    UserVO register(String email, String password, String phone, Integer role, String nickname);

    UserVO login(String email, String password);

    UserVO getProfile(Integer userId);

    void updateProfile(Integer userId, User user);

    void updatePassword(Integer userId, String oldPassword, String newPassword);
}
