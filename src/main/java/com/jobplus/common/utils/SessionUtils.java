package com.jobplus.common.utils;

import com.jobplus.entity.User;

import javax.servlet.http.HttpSession;

/**
 * Session工具类
 */
public class SessionUtils {

    private static final String USER_SESSION_KEY = "user";

    /**
     * 获取当前登录用户
     */
    public static User getCurrentUser(HttpSession session) {
        if (session == null) {
            return null;
        }
        Object user = session.getAttribute(USER_SESSION_KEY);
        if (user instanceof User) {
            return (User) user;
        }
        return null;
    }

    /**
     * 设置当前登录用户
     */
    public static void setCurrentUser(HttpSession session, User user) {
        session.setAttribute(USER_SESSION_KEY, user);
    }

    /**
     * 移除当前登录用户（登出）
     */
    public static void removeCurrentUser(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
    }

    /**
     * 获取当前登录用户ID
     */
    public static Integer getCurrentUserId(HttpSession session) {
        User user = getCurrentUser(session);
        return user != null ? user.getId() : null;
    }

    /**
     * 检查是否已登录
     */
    public static boolean isLogin(HttpSession session) {
        return getCurrentUser(session) != null;
    }
}
