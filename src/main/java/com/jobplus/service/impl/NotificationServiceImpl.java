package com.jobplus.service.impl;

import com.jobplus.entity.Notification;
import com.jobplus.mapper.NotificationMapper;
import com.jobplus.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
    @Autowired private NotificationMapper notificationMapper;

    @Override
    public void send(Integer userId, String type, String title, String content, Integer relatedId) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setType(type);
        n.setTitle(title);
        n.setContent(content);
        n.setRelatedId(relatedId);
        n.setIsRead(false);
        notificationMapper.insert(n);
    }

    @Override
    public List<Notification> getNotifications(Integer userId) {
        return notificationMapper.findByUserId(userId);
    }

    @Override
    public void markRead(Integer id, Integer userId) {
        notificationMapper.markRead(id);
    }

    @Override
    public void markAllRead(Integer userId) {
        notificationMapper.markAllRead(userId);
    }

    @Override
    public int countUnread(Integer userId) {
        return notificationMapper.countUnread(userId);
    }
}
