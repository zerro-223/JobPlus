package com.jobplus.mapper;

import com.jobplus.entity.Delivery;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface DeliveryMapper {

    @Select("SELECT * FROM delivery WHERE id = #{id}")
    Delivery findById(@Param("id") Integer id);

    @Select("SELECT * FROM delivery WHERE user_id = #{userId} AND position_id = #{positionId}")
    Delivery findByUserAndPosition(@Param("userId") Integer userId, @Param("positionId") Integer positionId);

    @Insert("INSERT INTO delivery (user_id, position_id, resume_id, status, company_read) " +
            "VALUES (#{userId}, #{positionId}, #{resumeId}, #{status}, #{companyRead})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Delivery delivery);

    @Update("UPDATE delivery SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    @Update("UPDATE delivery SET company_read = TRUE, read_at = NOW() WHERE id = #{id}")
    int markRead(@Param("id") Integer id);

    @Select("SELECT * FROM delivery WHERE user_id = #{userId} ORDER BY created_at DESC")
    List<Delivery> findByUserId(@Param("userId") Integer userId);

    /** Dynamic SQL via DeliveryMapper.xml */
    List<Delivery> findByCompanyId(@Param("companyId") Integer companyId);

    @Select("SELECT COUNT(*) FROM delivery")
    long count();
}
