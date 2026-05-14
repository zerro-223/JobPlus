package com.jobplus.service;

import com.jobplus.entity.Notification;

import java.util.List;

public interface NotificationService {
    void send(Integer userId, String type, String title, String content, Integer relatedId);
    List<Notification> getNotifications(Integer userId);
    void markRead(Integer id, Integer userId);
    void markAllRead(Integer userId);
    int countUnread(Integer userId);
}
