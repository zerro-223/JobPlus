package com.jobplus.mapper;

import com.jobplus.entity.Resume;
import org.apache.ibatis.annotations.*;

public interface ResumeMapper {

    @Select("SELECT * FROM resume WHERE id = #{id}")
    Resume findById(@Param("id") Integer id);

    @Select("SELECT * FROM resume WHERE user_id = #{userId}")
    Resume findByUserId(@Param("userId") Integer userId);

    @Insert("INSERT INTO resume (user_id, real_name, gender, birth_date, phone, email, education, school, major, " +
            "graduation_year, self_evaluation, skill_tags, attachment_url, attachment_name, is_public) " +
            "VALUES (#{userId}, #{realName}, #{gender}, #{birthDate}, #{phone}, #{email}, #{education}, #{school}, " +
            "#{major}, #{graduationYear}, #{selfEvaluation}, #{skillTags}, #{attachmentUrl}, #{attachmentName}, #{isPublic})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Resume resume);

    @Update("UPDATE resume SET real_name=#{realName}, gender=#{gender}, birth_date=#{birthDate}, phone=#{phone}, " +
            "email=#{email}, education=#{education}, school=#{school}, major=#{major}, " +
            "graduation_year=#{graduationYear}, self_evaluation=#{selfEvaluation}, skill_tags=#{skillTags}, " +
            "is_public=#{isPublic} WHERE id=#{id}")
    int update(Resume resume);
}
