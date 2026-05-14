package com.jobplus.controller;

import com.jobplus.common.annotation.RequireRole;
import com.jobplus.common.dto.*;
import com.jobplus.entity.*;
import com.jobplus.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    @Autowired private NotificationService notificationService;

    @RequireRole(0)
    @GetMapping
    public Result<List<Notification>> list(HttpSession session) {
        User u = (User) session.getAttribute("user");
        return Result.success(notificationService.getNotifications(u.getId()));
    }

    @RequireRole(0)
    @PutMapping("/{id}/read")
    public Result<Void> markRead(@PathVariable Integer id, HttpSession session) {
        User u = (User) session.getAttribute("user");
        notificationService.markRead(id, u.getId());
        return Result.success();
    }

    @RequireRole(0)
    @PutMapping("/read-all")
    public Result<Void> markAllRead(HttpSession session) {
        User u = (User) session.getAttribute("user");
        notificationService.markAllRead(u.getId());
        return Result.success();
    }

    @RequireRole(0)
    @GetMapping("/unread-count")
    public Result<Integer> unreadCount(HttpSession session) {
        User u = (User) session.getAttribute("user");
        return Result.success(notificationService.countUnread(u.getId()));
    }
}
