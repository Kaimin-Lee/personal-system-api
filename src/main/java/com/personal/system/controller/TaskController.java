package com.personal.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.personal.system.entity.Task;
import com.personal.system.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    /**
     * 获取当前用户的所有任务
     */
    @GetMapping("/list")
    public Map<String, Object> getList(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId"); // 从拦截器中拿到的用户ID

        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getUserId, userId)
                .orderByAsc(Task::getSortOrder) // 预留排序字段
                .orderByDesc(Task::getCreateTime);

        List<Task> list = taskService.list(wrapper);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "获取成功");
        result.put("data", list);
        return result;
    }

    /**
     * 新增任务
     */
    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Task task, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        task.setUserId(userId);
        task.setStatus((byte) 0); // 默认状态为 0 (待办)

        taskService.save(task);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "新增成功");
        return result;
    }

    /**
     * 更新任务状态 (拖拽时调用)
     */
    @PutMapping("/updateStatus")
    public Map<String, Object> updateStatus(@RequestBody Task task, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");

        // 安全校验：只能修改自己的任务
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getId, task.getId())
                .eq(Task::getUserId, userId);

        Task existTask = taskService.getOne(wrapper);
        Map<String, Object> result = new HashMap<>();

        if (existTask != null) {
            existTask.setStatus(task.getStatus());
            // 如果传了新排序也可以在这里更新
            taskService.updateById(existTask);
            result.put("code", 200);
            result.put("message", "状态更新成功");
        } else {
            result.put("code", 403);
            result.put("message", "无权操作或任务不存在");
        }
        return result;
    }
}