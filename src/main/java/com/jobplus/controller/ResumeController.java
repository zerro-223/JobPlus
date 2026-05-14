package com.jobplus.controller;

import com.jobplus.common.dto.*;
import com.jobplus.entity.*;
import com.jobplus.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import com.jobplus.common.annotation.RequireRole;

@RestController
@RequestMapping("/api/v1/resume")
public class ResumeController {
    @Autowired private ResumeService resumeService;

    @RequireRole(0)
    @GetMapping
    public Result<ResumeVO> getResume(HttpSession session) {
        User u = (User) session.getAttribute("user");
        return Result.success(resumeService.getResume(u.getId()));
    }

    @PutMapping
    public Result<Void> saveResume(@RequestBody Resume resume, HttpSession session) {
        User u = (User) session.getAttribute("user");
        resumeService.saveResume(resume, u.getId());
        return Result.success();
    }

    @RequireRole(0)
    @PostMapping("/attachment")
    public Result<String> uploadAttachment(@RequestParam("file") MultipartFile file, HttpSession session) {
        User u = (User) session.getAttribute("user");
        String url = resumeService.uploadAttachment(u.getId(), file);
        return Result.success(url);
    }

    @PutMapping("/education")
    public Result<Void> saveEducation(@RequestBody List<EducationExperience> list, HttpSession session) {
        User u = (User) session.getAttribute("user");
        ResumeVO vo = resumeService.getResume(u.getId());
        if (vo == null) return Result.error(400, "请先创建简历");
        resumeService.saveEducationList(vo.getId(), list);
        return Result.success();
    }

    @PutMapping("/work")
    public Result<Void> saveWork(@RequestBody List<WorkExperience> list, HttpSession session) {
        User u = (User) session.getAttribute("user");
        ResumeVO vo = resumeService.getResume(u.getId());
        if (vo == null) return Result.error(400, "请先创建简历");
        resumeService.saveWorkList(vo.getId(), list);
        return Result.success();
    }

    @PutMapping("/project")
    public Result<Void> saveProject(@RequestBody List<ProjectExperience> list, HttpSession session) {
        User u = (User) session.getAttribute("user");
        ResumeVO vo = resumeService.getResume(u.getId());
        if (vo == null) return Result.error(400, "请先创建简历");
        resumeService.saveProjectList(vo.getId(), list);
        return Result.success();
    }
}
