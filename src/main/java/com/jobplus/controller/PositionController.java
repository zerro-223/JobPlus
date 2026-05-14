package com.jobplus.controller;

import com.jobplus.common.dto.*;
import com.jobplus.entity.JobCategory;
import com.jobplus.entity.User;
import com.jobplus.mapper.JobCategoryMapper;
import com.jobplus.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import com.jobplus.common.annotation.RequireRole;

@RestController
@RequestMapping("/api/v1/positions")
public class PositionController {
    @Autowired private PositionService positionService;
    @Autowired private JobCategoryMapper jobCategoryMapper;

    @GetMapping
    public Result<PageResult<PositionVO>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) java.math.BigDecimal salaryMin,
            @RequestParam(required = false) String education,
            @RequestParam(required = false) Integer categoryId,
            HttpSession session) {
        PositionQuery query = new PositionQuery();
        query.setPage(page); query.setSize(size);
        query.setKeyword(keyword); query.setCity(city);
        query.setSalaryMin(salaryMin); query.setEducation(education);
        query.setCategoryId(categoryId);
        query.setStatus(1); // 只查招聘中
        PageResult<PositionVO> result = positionService.search(query);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    public Result<PositionVO> detail(@PathVariable Integer id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        PositionVO vo = positionService.getDetail(id, user != null ? user.getId() : null);
        return Result.success(vo);
    }

    @RequireRole(2)
    @PostMapping
    public Result<PositionVO> create(@RequestBody PositionCreateReq req, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user.getRole() != 2) return Result.error(403, "仅企业用户可发布职位");
        PositionVO vo = positionService.create(req, user);
        return Result.success(vo);
    }

    @RequireRole(2)
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Integer id, @RequestBody PositionUpdateReq req, HttpSession session) {
        User user = (User) session.getAttribute("user");
        req.setId(id);
        positionService.update(req, user);
        return Result.success();
    }

    @RequireRole(2)
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Integer id, @RequestParam Integer status, HttpSession session) {
        User user = (User) session.getAttribute("user");
        positionService.updateStatus(id, status, user);
        return Result.success();
    }

    @GetMapping("/categories")
    public Result<List<JobCategory>> categories() {
        return Result.success(jobCategoryMapper.findAll());
    }
}
