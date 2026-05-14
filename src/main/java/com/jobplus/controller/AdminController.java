package com.jobplus.controller;

import com.jobplus.common.dto.*;
import com.jobplus.entity.*;
import com.jobplus.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import com.jobplus.common.annotation.RequireRole;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @Autowired private AdminService adminService;

    private void checkAdmin(HttpSession session) {
        User u = (User) session.getAttribute("user");
        if (u == null || u.getRole() != 3) throw new RuntimeException("无管理员权限");
    }

    @RequireRole(3)
    @GetMapping("/companies/pending")
    public Result<List<Company>> getPendingCompanies(HttpSession session) {
        checkAdmin(session);
        return Result.success(adminService.getPendingCompanies());
    }

    @RequireRole(3)
    @PutMapping("/companies/{id}/audit")
    public Result<Void> auditCompany(@PathVariable Integer id, @RequestParam boolean approved,
                                     @RequestParam(required = false) String rejectReason, HttpSession session) {
        User u = (User) session.getAttribute("user");
        checkAdmin(session);
        adminService.auditCompany(id, approved, rejectReason, u);
        return Result.success();
    }

    @RequireRole(3)
    @GetMapping("/positions/pending")
    public Result<List<PositionVO>> getPendingPositions(HttpSession session) {
        checkAdmin(session);
        return Result.success(adminService.getPendingPositions());
    }

    @RequireRole(3)
    @PutMapping("/positions/{id}/audit")
    public Result<Void> auditPosition(@PathVariable Integer id, @RequestParam boolean approved,
                                      @RequestParam(required = false) String rejectReason, HttpSession session) {
        User u = (User) session.getAttribute("user");
        checkAdmin(session);
        adminService.auditPosition(id, approved, rejectReason, u);
        return Result.success();
    }

    @RequireRole(3)
    @GetMapping("/users")
    public Result<List<User>> getUsers(HttpSession session) {
        checkAdmin(session);
        return Result.success(adminService.getUsers());
    }

    @RequireRole(3)
    @PutMapping("/users/{id}/status")
    public Result<Void> updateUserStatus(@PathVariable Integer id, @RequestParam Integer status, HttpSession session) {
        checkAdmin(session);
        adminService.updateUserStatus(id, status);
        return Result.success();
    }

    @RequireRole(3)
    @GetMapping("/stats")
    public Result<StatsVO> stats(HttpSession session) {
        checkAdmin(session);
        return Result.success(adminService.getStats());
    }
}
