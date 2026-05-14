package com.jobplus.controller;

import com.jobplus.common.dto.*;
import com.jobplus.entity.User;
import com.jobplus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired private UserService userService;

    @PostMapping("/register")
    public Result<UserVO> register(@RequestBody UserRegisterReq req) {
        User user = userService.register(req);
        return Result.success(new UserVO(user));
    }

    @PostMapping("/login")
    public Result<UserVO> login(@RequestBody UserRegisterReq req, HttpSession session) {
        User user = userService.login(req.getEmail(), req.getPassword());
        session.setAttribute("user", user);
        UserVO vo = new UserVO(user);
        return Result.success(vo);
    }

    @RequireRole(0)
    @GetMapping("/profile")
    public Result<UserVO> profile(HttpSession session) {
        User u = (User) session.getAttribute("user");
        User user = userService.getProfile(u.getId());
        return Result.success(new UserVO(user));
    }

    @RequireRole(0)
    @PutMapping("/profile")
    public Result<Void> updateProfile(@RequestBody User user, HttpSession session) {
        User u = (User) session.getAttribute("user");
        user.setId(u.getId());
        userService.updateProfile(user);
        return Result.success();
    }

    @RequireRole(0)
    @PutMapping("/password")
    public Result<Void> updatePassword(@RequestParam String oldPwd, @RequestParam String newPwd, HttpSession session) {
        User u = (User) session.getAttribute("user");
        userService.updatePassword(u.getId(), oldPwd, newPwd);
        return Result.success();
    }

    @RequireRole(0)
    @PostMapping("/logout")
    public Result<Void> logout(HttpSession session) {
        session.invalidate();
        return Result.success();
    }
}
