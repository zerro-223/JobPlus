package com.jobplus.mapper;

import com.jobplus.entity.Interview;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface InterviewMapper {

    @Select("SELECT * FROM interview WHERE id = #{id}")
    Interview findById(@Param("id") Integer id);

    @Insert("INSERT INTO interview (delivery_id, company_id, user_id, interview_time, location, contact, contact_phone, remark, status) " +
            "VALUES (#{deliveryId}, #{companyId}, #{userId}, #{interviewTime}, #{location}, #{contact}, #{contactPhone}, #{remark}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Interview interview);

    @Update("UPDATE interview SET status=#{status} WHERE id=#{id}")
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    @Select("SELECT * FROM interview WHERE user_id = #{userId} ORDER BY created_at DESC")
    List<Interview> findByUserId(@Param("userId") Integer userId);

    @Select("SELECT * FROM interview WHERE company_id = #{companyId} ORDER BY created_at DESC")
    List<Interview> findByCompanyId(@Param("companyId") Integer companyId);
}
