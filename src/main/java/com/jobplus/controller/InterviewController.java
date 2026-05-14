package com.jobplus.controller;

import com.jobplus.common.dto.*;
import com.jobplus.entity.*;
import com.jobplus.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import com.jobplus.common.annotation.RequireRole;

@RestController
@RequestMapping("/api/v1/interviews")
public class InterviewController {
    @Autowired private InterviewService interviewService;

    @RequireRole(2)
    @PostMapping
    public Result<Interview> create(@RequestBody Interview interview, HttpSession session) {
        User u = (User) session.getAttribute("user");
        return Result.success(interviewService.createInterview(interview, u));
    }

    @RequireRole(0)
    @GetMapping
    public Result<List<Interview>> list(HttpSession session) {
        User u = (User) session.getAttribute("user");
        return Result.success(interviewService.getMyInterviews(u));
    }

    @RequireRole(1)
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Integer id, @RequestParam Integer status, HttpSession session) {
        User u = (User) session.getAttribute("user");
        interviewService.updateStatus(id, status, u);
        return Result.success();
    }
}
