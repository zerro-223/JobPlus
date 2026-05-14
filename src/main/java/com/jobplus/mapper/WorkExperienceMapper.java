package com.jobplus.mapper;

import com.jobplus.entity.WorkExperience;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface WorkExperienceMapper {

    @Select("SELECT * FROM work_experience WHERE resume_id = #{resumeId} ORDER BY sort_order")
    List<WorkExperience> findByResumeId(@Param("resumeId") Integer resumeId);

    @Insert("INSERT INTO work_experience (resume_id, company, position, start_date, end_date, description, sort_order) " +
            "VALUES (#{resumeId}, #{company}, #{position}, #{startDate}, #{endDate}, #{description}, #{sortOrder})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(WorkExperience exp);

    @Update("UPDATE work_experience SET company=#{company}, position=#{position}, " +
            "start_date=#{startDate}, end_date=#{endDate}, description=#{description}, sort_order=#{sortOrder} WHERE id=#{id}")
    int update(WorkExperience exp);

    @Delete("DELETE FROM work_experience WHERE resume_id = #{resumeId}")
    int deleteByResumeId(@Param("resumeId") Integer resumeId);
}
