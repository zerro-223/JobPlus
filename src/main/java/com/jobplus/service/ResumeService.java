package com.jobplus.service;

import com.jobplus.entity.EducationExperience;
import com.jobplus.entity.ProjectExperience;
import com.jobplus.entity.Resume;
import com.jobplus.entity.WorkExperience;
import com.jobplus.entity.vo.ResumeVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 简历服务接口
 */
public interface ResumeService {

    ResumeVO getResume(Integer userId);

    void saveResume(Resume resume, Integer userId);

    String uploadAttachment(Integer userId, MultipartFile file);

    void saveEducationList(Integer userId, List<EducationExperience> list);

    void saveWorkList(Integer userId, List<WorkExperience> list);

    void saveProjectList(Integer userId, List<ProjectExperience> list);
}
