package com.personal.system.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.system.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行 OPTIONS 预检请求 (解决跨域问题)
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Long userId = JwtUtils.getUserIdFromToken(token);

            if (userId != null) {
                // Token 验证通过，把 userId 存入 request，方便 Controller 直接拿
                request.setAttribute("userId", userId);
                return true;
            }
        }

        // Token 验证失败或过期，返回 401 状态码
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> result = new HashMap<>();
        result.put("code", 401);
        result.put("message", "登录已过期或未登录，请重新登录");
        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
        return false;
    }
}