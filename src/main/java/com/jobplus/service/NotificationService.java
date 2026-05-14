package com.jobplus.service;

import com.jobplus.entity.Notification;

import java.util.List;

/**
 * 通知服务接口
 */
public interface NotificationService {

    List<Notification> getNotifications(Integer userId);

    void markRead(Integer id, Integer userId);

    void markAllRead(Integer userId);

    int countUnread(Integer userId);

    void send(String type, Integer userId, String title, String content, Integer relatedId);
}
