package com.personal.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.personal.system.entity.Countdown;
import com.personal.system.entity.Task;
import com.personal.system.service.ICountdownService;
import com.personal.system.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private ITaskService taskService;

    @Autowired
    private ICountdownService countdownService;

    /**
     * 核心工具方法：从请求头中提取当前登录的 User ID
     * 这就是实现数据隔离的门神！
     */
    private Long getCurrentUserId(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        // 前端传来的格式通常是: "Bearer mock-token-1"
        if (authHeader != null && authHeader.startsWith("Bearer mock-token-")) {
            String userIdStr = authHeader.substring("Bearer mock-token-".length());
            return Long.parseLong(userIdStr);
        }
        throw new RuntimeException("未授权的访问：Token无效");
    }

    /**
     * 获取看板数据
     */
    @GetMapping("/data")
    public Map<String, Object> getDashboardData(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        try {
            Long userId = getCurrentUserId(request);

            LambdaQueryWrapper<Task> taskWrapper = new LambdaQueryWrapper<>();
            taskWrapper.eq(Task::getUserId, userId)
                    .eq(Task::getStatus, 1)
                    .orderByDesc(Task::getPriority)
                    .last("LIMIT 5");
            List<Task> activeTasks = taskService.list(taskWrapper);

            LambdaQueryWrapper<Countdown> countdownWrapper = new LambdaQueryWrapper<>();
            countdownWrapper.eq(Countdown::getUserId, userId)
                    .eq(Countdown::getIsPinned, 1)
                    .orderByAsc(Countdown::getTargetDate)
                    .last("LIMIT 5");
            List<Countdown> pinnedCountdowns = countdownService.list(countdownWrapper);

            Map<String, Object> data = new HashMap<>();
            data.put("tasks", activeTasks);
            data.put("countdowns", pinnedCountdowns);

            result.put("code", 200);
            result.put("message", "获取成功");
            result.put("data", data);

        } catch (Exception e) {
            result.put("code", 401);
            result.put("message", e.getMessage());
        }

        return result;
    }
}