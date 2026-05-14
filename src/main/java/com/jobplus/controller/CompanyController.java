package com.jobplus.controller;

import com.jobplus.common.dto.*;
import com.jobplus.entity.*;
import com.jobplus.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import com.jobplus.common.annotation.RequireRole;

@RestController
@RequestMapping("/api/v1/company")
public class CompanyController {
    @Autowired private CompanyService companyService;

    @RequireRole(2)
    @GetMapping("/profile")
    public Result<Company> profile(HttpSession session) {
        User u = (User) session.getAttribute("user");
        return Result.success(companyService.getProfile(u.getId()));
    }

    @RequireRole(2)
    @PutMapping("/profile")
    public Result<Void> updateProfile(@RequestBody Company company, HttpSession session) {
        User u = (User) session.getAttribute("user");
        companyService.updateProfile(company, u);
        return Result.success();
    }

    @RequireRole(2)
    @PostMapping("/certify")
    public Result<Void> submitCertification(@RequestBody CompanyCertification cert, HttpSession session) {
        User u = (User) session.getAttribute("user");
        companyService.submitCertification(cert, u);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Company> getPublicInfo(@PathVariable Integer id) {
        return Result.success(companyService.getPublicInfo(id));
    }

    @GetMapping("/{id}/positions")
    public Result<List<Position>> getPositions(@PathVariable Integer id) {
        return Result.success(companyService.getPositions(id));
    }

    @RequireRole(2)
    @GetMapping("/deliveries")
    public Result<List<DeliveryVO>> getDeliveries(HttpSession session) {
        User u = (User) session.getAttribute("user");
        return Result.success(companyService.getDeliveries(u));
    }
}
