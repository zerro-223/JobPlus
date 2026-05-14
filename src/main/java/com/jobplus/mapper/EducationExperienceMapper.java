package com.jobplus.mapper;

import com.jobplus.entity.EducationExperience;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface EducationExperienceMapper {

    @Select("SELECT * FROM education_experience WHERE resume_id = #{resumeId} ORDER BY sort_order")
    List<EducationExperience> findByResumeId(@Param("resumeId") Integer resumeId);

    @Insert("INSERT INTO education_experience (resume_id, school, major, degree, start_date, end_date, description, sort_order) " +
            "VALUES (#{resumeId}, #{school}, #{major}, #{degree}, #{startDate}, #{endDate}, #{description}, #{sortOrder})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(EducationExperience exp);

    @Update("UPDATE education_experience SET school=#{school}, major=#{major}, degree=#{degree}, " +
            "start_date=#{startDate}, end_date=#{endDate}, description=#{description}, sort_order=#{sortOrder} WHERE id=#{id}")
    int update(EducationExperience exp);

    @Delete("DELETE FROM education_experience WHERE resume_id = #{resumeId}")
    int deleteByResumeId(@Param("resumeId") Integer resumeId);
}
