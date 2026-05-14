package com.jobplus.common.dto;

import com.jobplus.entity.EducationExperience;
import com.jobplus.entity.ProjectExperience;
import com.jobplus.entity.Resume;
import com.jobplus.entity.WorkExperience;

import java.util.List;

/**
 * 简历视图对象（含关联的经历列表）
 */
public class ResumeVO extends Resume {

    private List<EducationExperience> educationList;
    private List<WorkExperience> workList;
    private List<ProjectExperience> projectList;

    public ResumeVO() {}

    public ResumeVO(Resume resume) {
        this.setId(resume.getId());
        this.setUserId(resume.getUserId());
        this.setRealName(resume.getRealName());
        this.setGender(resume.getGender());
        this.setBirthDate(resume.getBirthDate());
        this.setPhone(resume.getPhone());
        this.setEmail(resume.getEmail());
        this.setEducation(resume.getEducation());
        this.setSchool(resume.getSchool());
        this.setMajor(resume.getMajor());
        this.setGraduationYear(resume.getGraduationYear());
        this.setSelfEvaluation(resume.getSelfEvaluation());
        this.setSkillTags(resume.getSkillTags());
        this.setAttachmentUrl(resume.getAttachmentUrl());
        this.setAttachmentName(resume.getAttachmentName());
        this.setIsPublic(resume.getIsPublic());
        this.setCreatedAt(resume.getCreatedAt());
        this.setUpdatedAt(resume.getUpdatedAt());
    }

    public List<EducationExperience> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<EducationExperience> educationList) {
        this.educationList = educationList;
    }

    public List<WorkExperience> getWorkList() {
        return workList;
    }

    public void setWorkList(List<WorkExperience> workList) {
        this.workList = workList;
    }

    public List<ProjectExperience> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectExperience> projectList) {
        this.projectList = projectList;
    }

    @Override
    public String toString() {
        return "ResumeVO{" +
                "id=" + getId() +
                ", realName='" + getRealName() + '\'' +
                ", educationList=" + educationList +
                ", workList=" + workList +
                ", projectList=" + projectList +
                '}';
    }
}
