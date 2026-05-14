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
 * 角色权限拦截器 — 仅拦截标注了 @RequireRole 的方法
 * 未标注的方法不需要登录即可访问（Controller 内部自行处理 auth）
 */
public class AuthInterceptor implements HandlerInterceptor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        RequireRole requireRole = method.getAnnotation(RequireRole.class);

        // 没有 @RequireRole 注解的方法，放行（不要求登录）
        if (requireRole == null) {
            return true;
        }

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        // 需要特定角色但未登录
        if (user == null) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);
            response.getWriter().write(
                objectMapper.writeValueAsString(Result.error(401, "请先登录")));
            return false;
        }

        // 角色校验
        int requiredRole = requireRole.value();
        // @RequireRole(0) 表示任意已登录用户均可访问
        if (requiredRole == 0) {
            return true;
        }
        // 管理员(role=3)可以访问所有带角色限制的接口
        if (user.getRole() != requiredRole && user.getRole() != 3) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(403);
            response.getWriter().write(
                objectMapper.writeValueAsString(Result.error(403, "权限不足")));
            return false;
        }

        return true;
    }
}
