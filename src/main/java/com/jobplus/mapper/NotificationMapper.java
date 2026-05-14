package com.jobplus.mapper;

import com.jobplus.entity.Notification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通知消息 Mapper
 */
public interface NotificationMapper {

    Notification findById(@Param("id") Integer id);

    int insert(Notification notification);

    int updateReadStatus(@Param("id") Integer id, @Param("isRead") Boolean isRead);

    int markAllRead(@Param("userId") Integer userId);

    List<Notification> findByUserId(@Param("userId") Integer userId);

    int countUnread(@Param("userId") Integer userId);
}
