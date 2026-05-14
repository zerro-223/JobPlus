package com.jobplus.service.impl;

import com.jobplus.common.dto.ResumeVO;
import com.jobplus.common.exception.BusinessException;
import com.jobplus.entity.*;
import com.jobplus.mapper.*;
import com.jobplus.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ResumeServiceImpl implements ResumeService {
    @Autowired private ResumeMapper resumeMapper;
    @Autowired private EducationExperienceMapper educationMapper;
    @Autowired private WorkExperienceMapper workMapper;
    @Autowired private ProjectExperienceMapper projectMapper;

    @Override
    public ResumeVO getResume(Integer userId) {
        Resume resume = resumeMapper.findByUserId(userId);
        if (resume == null) return null;
        ResumeVO vo = new ResumeVO();
        copyResume(resume, vo);
        vo.setEducationList(educationMapper.findByResumeId(resume.getId()));
        vo.setWorkList(workMapper.findByResumeId(resume.getId()));
        vo.setProjectList(projectMapper.findByResumeId(resume.getId()));
        return vo;
    }

    @Override
    public void saveResume(Resume resume, Integer userId) {
        Resume existing = resumeMapper.findByUserId(userId);
        if (existing != null) {
            resume.setId(existing.getId());
            resume.setUserId(userId);
            resume.setUpdatedAt(new Date());
            resumeMapper.update(resume);
        } else {
            resume.setUserId(userId);
            resumeMapper.insert(resume);
        }
    }

    @Override
    public void saveEducationList(Integer resumeId, List<EducationExperience> list) {
        educationMapper.deleteByResumeId(resumeId);
        if (list != null) {
            for (EducationExperience e : list) { e.setResumeId(resumeId); educationMapper.insert(e); }
        }
    }

    @Override
    public void saveWorkList(Integer resumeId, List<WorkExperience> list) {
        workMapper.deleteByResumeId(resumeId);
        if (list != null) {
            for (WorkExperience w : list) { w.setResumeId(resumeId); workMapper.insert(w); }
        }
    }

    @Override
    public void saveProjectList(Integer resumeId, List<ProjectExperience> list) {
        projectMapper.deleteByResumeId(resumeId);
        if (list != null) {
            for (ProjectExperience p : list) { p.setResumeId(resumeId); projectMapper.insert(p); }
        }
    }

    @Override
    public String uploadAttachment(Integer userId, MultipartFile file) {
        if (file == null || file.isEmpty()) throw new BusinessException(400, "文件为空");
        String originalName = file.getOriginalFilename();
        String ext = originalName != null && originalName.contains(".")
                ? originalName.substring(originalName.lastIndexOf(".")) : "";
        String fileName = UUID.randomUUID().toString() + ext;
        String uploadDir = System.getProperty("catalina.base") != null
                ? System.getProperty("catalina.base") + "/webapps/uploads/"
                : System.getProperty("user.dir") + "/uploads/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
        try {
            file.transferTo(new File(dir, fileName));
        } catch (IOException e) {
            throw new BusinessException(500, "文件上传失败");
        }
        String url = "/uploads/" + fileName;
        Resume resume = resumeMapper.findByUserId(userId);
        if (resume == null) {
            resume = new Resume();
            resume.setUserId(userId);
            resume.setAttachmentUrl(url);
            resume.setAttachmentName(originalName);
            resumeMapper.insert(resume);
        } else {
            resume.setAttachmentUrl(url);
            resume.setAttachmentName(originalName);
            resumeMapper.update(resume);
        }
        return url;
    }

    private void copyResume(Resume src, ResumeVO dst) {
        dst.setId(src.getId()); dst.setUserId(src.getUserId());
        dst.setRealName(src.getRealName()); dst.setGender(src.getGender());
        dst.setBirthDate(src.getBirthDate()); dst.setPhone(src.getPhone());
        dst.setEmail(src.getEmail()); dst.setEducation(src.getEducation());
        dst.setSchool(src.getSchool()); dst.setMajor(src.getMajor());
        dst.setGraduationYear(src.getGraduationYear());
        dst.setSelfEvaluation(src.getSelfEvaluation());
        dst.setSkillTags(src.getSkillTags());
        dst.setAttachmentUrl(src.getAttachmentUrl());
        dst.setAttachmentName(src.getAttachmentName());
        dst.setIsPublic(src.getIsPublic());
    }
}
