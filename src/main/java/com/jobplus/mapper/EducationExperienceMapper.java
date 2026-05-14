package com.jobplus.mapper;

import com.jobplus.entity.EducationExperience;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教育经历 Mapper
 */
public interface EducationExperienceMapper {

    List<EducationExperience> findByResumeId(@Param("resumeId") Integer resumeId);

    int insert(EducationExperience edu);

    int batchInsert(@Param("list") List<EducationExperience> list);

    int deleteByResumeId(@Param("resumeId") Integer resumeId);
}
