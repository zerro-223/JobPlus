package com.jobplus.mapper;

import com.jobplus.entity.ProjectExperience;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProjectExperienceMapper {

    @Select("SELECT * FROM project_experience WHERE resume_id = #{resumeId} ORDER BY sort_order")
    List<ProjectExperience> findByResumeId(@Param("resumeId") Integer resumeId);

    @Insert("INSERT INTO project_experience (resume_id, name, role, start_date, end_date, description, url, sort_order) " +
            "VALUES (#{resumeId}, #{name}, #{role}, #{startDate}, #{endDate}, #{description}, #{url}, #{sortOrder})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ProjectExperience exp);

    @Update("UPDATE project_experience SET name=#{name}, role=#{role}, " +
            "start_date=#{startDate}, end_date=#{endDate}, description=#{description}, url=#{url}, sort_order=#{sortOrder} WHERE id=#{id}")
    int update(ProjectExperience exp);

    @Delete("DELETE FROM project_experience WHERE resume_id = #{resumeId}")
    int deleteByResumeId(@Param("resumeId") Integer resumeId);
}
