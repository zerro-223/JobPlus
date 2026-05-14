package com.jobplus.mapper;

import com.jobplus.entity.WorkExperience;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工作经历 Mapper
 */
public interface WorkExperienceMapper {

    List<WorkExperience> findByResumeId(@Param("resumeId") Integer resumeId);

    int insert(WorkExperience work);

    int batchInsert(@Param("list") List<WorkExperience> list);

    int deleteByResumeId(@Param("resumeId") Integer resumeId);
}
