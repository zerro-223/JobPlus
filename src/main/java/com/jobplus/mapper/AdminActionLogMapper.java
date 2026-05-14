package com.jobplus.mapper;

import com.jobplus.entity.AdminActionLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AdminActionLogMapper {

    @Insert("INSERT INTO admin_action_log (admin_id, action_type, target_id, target_type, detail, ip_address) " +
            "VALUES (#{adminId}, #{actionType}, #{targetId}, #{targetType}, #{detail}, #{ipAddress})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AdminActionLog log);

    @Select("SELECT * FROM admin_action_log WHERE admin_id = #{adminId} ORDER BY created_at DESC")
    List<AdminActionLog> findByAdminId(@Param("adminId") Integer adminId);
}
