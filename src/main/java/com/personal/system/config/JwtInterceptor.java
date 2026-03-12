package com.personal.system.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.system.utils.JwtUtils;
import com.personal.system.utils.UserContext;
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
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Long userId = JwtUtils.getUserIdFromToken(token);

            if (userId != null) {
                // 【核心改造】：不再放入 request，而是直接绑定到当前线程！
                UserContext.setUserId(userId);
                return true;
            }
        }

        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> result = new HashMap<>();
        result.put("code", 401);
        result.put("message", "登录已过期或未登录，请重新登录");
        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
        return false;
    }

    /**
     * 【核心新增】：请求处理完毕后（无论是正常返回还是抛出异常），必须清理 ThreadLocal！
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.removeUserId();
    }
}