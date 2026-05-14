package com.jobplus.common.interceptor;

import com.jobplus.common.annotation.RequireRole;
import com.jobplus.common.dto.Result;
import com.jobplus.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * 登录认证 & 角色权限拦截器
 */
public class AuthInterceptor implements HandlerInterceptor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        // 仅拦截 Controller 方法
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // ① 登录校验：所有 /api/v1/** 请求都需要登录
        if (user == null) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);
            response.getWriter().write(
                objectMapper.writeValueAsString(Result.error(401, "请先登录")));
            return false;
        }

        // ② 角色校验
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        RequireRole requireRole = method.getAnnotation(RequireRole.class);
        if (requireRole != null) {
            int requiredRole = requireRole.value();
            if (user.getRole() != requiredRole && user.getRole() != 3) {
                // 非指定角色且非管理员 → 403
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(403);
                response.getWriter().write(
                    objectMapper.writeValueAsString(Result.error(403, "权限不足")));
                return false;
            }
        }

        return true;
    }
}
