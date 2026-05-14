package com.jobplus.mapper;

import com.jobplus.entity.ProjectExperience;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目经验 Mapper
 */
public interface ProjectExperienceMapper {

    List<ProjectExperience> findByResumeId(@Param("resumeId") Integer resumeId);

    int insert(ProjectExperience project);

    int batchInsert(@Param("list") List<ProjectExperience> list);

    int deleteByResumeId(@Param("resumeId") Integer resumeId);
}
