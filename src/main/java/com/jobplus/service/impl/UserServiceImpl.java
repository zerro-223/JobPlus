package com.jobplus.service.impl;

import com.jobplus.common.dto.UserRegisterReq;
import com.jobplus.common.exception.BusinessException;
import com.jobplus.entity.User;
import com.jobplus.mapper.UserMapper;
import com.jobplus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 用户服务实现
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 用户注册
     * 校验邮箱唯一性，加密密码，插入用户
     */
    @Override
    public User register(UserRegisterReq req) {
        // 校验邮箱唯一性
        User existing = userMapper.findByEmail(req.getEmail());
        if (existing != null) {
            throw new BusinessException(400, "该邮箱已被注册");
        }

        // 创建用户
        User user = new User();
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setNickname(req.getNickname() != null ? req.getNickname() : req.getEmail());
        user.setRole(req.getRole() != null ? req.getRole() : 0);
        user.setStatus(1); // 默认启用
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        userMapper.insert(user);

        // 清除密码后返回
        user.setPassword(null);
        return user;
    }

    /**
     * 用户登录
     * 查邮箱，验证密码，检查状态
     */
    @Override
    public User login(String email, String password) {
        User user = userMapper.findByEmail(email);
        if (user == null) {
            throw new BusinessException(400, "邮箱或密码错误");
        }

        // 验证密码
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException(400, "邮箱或密码错误");
        }

        // 检查状态
        if (user.getStatus() != 1) {
            throw new BusinessException(403, "账号已被禁用，请联系管理员");
        }

        // 清除密码后返回
        user.setPassword(null);
        return user;
    }

    /**
     * 获取用户信息
     */
    @Override
    public User getProfile(Integer userId) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        user.setPassword(null);
        return user;
    }

    /**
     * 更新用户资料（昵称、手机、头像）
     */
    @Override
    public void updateProfile(User user) {
        User existing = userMapper.findById(user.getId());
        if (existing == null) {
            throw new BusinessException(404, "用户不存在");
        }
        userMapper.update(user);
    }

    /**
     * 修改密码
     */
    @Override
    public void updatePassword(Integer userId, String oldPwd, String newPwd) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }

        // 验证旧密码
        if (!passwordEncoder.matches(oldPwd, user.getPassword())) {
            throw new BusinessException(400, "原密码错误");
        }

        // 更新新密码
        userMapper.updatePassword(userId, passwordEncoder.encode(newPwd));
    }
}
