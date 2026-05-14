package com.jobplus.mapper;

import com.jobplus.entity.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface NotificationMapper {

    @Select("SELECT * FROM notification WHERE id = #{id}")
    Notification findById(@Param("id") Integer id);

    @Insert("INSERT INTO notification (user_id, type, title, content, related_id, is_read) " +
            "VALUES (#{userId}, #{type}, #{title}, #{content}, #{relatedId}, #{isRead})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Notification notification);

    @Update("UPDATE notification SET is_read = TRUE, read_at = NOW() WHERE id = #{id}")
    int markRead(@Param("id") Integer id);

    @Update("UPDATE notification SET is_read = TRUE, read_at = NOW() WHERE user_id = #{userId} AND is_read = FALSE")
    int markAllRead(@Param("userId") Integer userId);

    @Select("SELECT * FROM notification WHERE user_id = #{userId} ORDER BY created_at DESC")
    List<Notification> findByUserId(@Param("userId") Integer userId);

    @Select("SELECT COUNT(*) FROM notification WHERE user_id = #{userId} AND is_read = FALSE")
    int countUnread(@Param("userId") Integer userId);
}
