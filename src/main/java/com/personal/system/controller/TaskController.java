package com.personal.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.personal.system.common.Result;
import com.personal.system.entity.Task;
import com.personal.system.service.ITaskService;
import com.personal.system.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @GetMapping("/list")
    public Result<List<Task>> getList() {
        // 没有任何多余参数！想要 userId？直接从上下文里拿！
        Long userId = UserContext.getUserId();

        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getUserId, userId)
                .orderByAsc(Task::getSortOrder)
                .orderByDesc(Task::getCreateTime);

        List<Task> list = taskService.list(wrapper);

        return Result.success(list);
    }

    @PostMapping("/add")
    public Result<Void> add(@RequestBody Task task) {
        // 随时随地，直接获取当前登录用户
        task.setUserId(UserContext.getUserId());
        task.setStatus((byte) 0);
        taskService.save(task);

        return Result.success("新增成功", null);
    }
}