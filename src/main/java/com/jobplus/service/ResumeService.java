package com.jobplus.service;

import com.jobplus.common.dto.ResumeVO;
import com.jobplus.entity.*;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ResumeService {
    ResumeVO getResume(Integer userId);
    void saveResume(Resume resume, Integer userId);
    void saveEducationList(Integer resumeId, List<EducationExperience> list);
    void saveWorkList(Integer resumeId, List<WorkExperience> list);
    void saveProjectList(Integer resumeId, List<ProjectExperience> list);
    String uploadAttachment(Integer userId, MultipartFile file);
}
