package com.personal.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.personal.system.common.Result;
import com.personal.system.entity.Countdown;
import com.personal.system.entity.Task;
import com.personal.system.service.ICountdownService;
import com.personal.system.service.ITaskService;
import com.personal.system.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 获取看板数据
     */
    @GetMapping("/data")
    public Result<Map<String, Object>> getDashboardData() {
        Map<String, Object> result = new HashMap<>();

        try {
            Long userId = UserContext.getUserId();

            // 2. 纯粹的业务逻辑：查任务
            LambdaQueryWrapper<Task> taskWrapper = new LambdaQueryWrapper<>();
            taskWrapper.eq(Task::getUserId, userId)
                    .eq(Task::getStatus, 1) // 进行中的任务
                    .orderByDesc(Task::getPriority)
                    .last("LIMIT 5");
            List<Task> activeTasks = taskService.list(taskWrapper);

            // 3. 纯粹的业务逻辑：查倒数日
            LambdaQueryWrapper<Countdown> countdownWrapper = new LambdaQueryWrapper<>();
            countdownWrapper.eq(Countdown::getUserId, userId)
                    .eq(Countdown::getIsPinned, 1)
                    .orderByAsc(Countdown::getTargetDate)
                    .last("LIMIT 5");
            List<Countdown> pinnedCountdowns = countdownService.list(countdownWrapper);

            // 4. 封装返回
            Map<String, Object> data = new HashMap<>();
            data.put("tasks", activeTasks);
            data.put("countdowns", pinnedCountdowns);

            return Result.success("获取成功", data);

        } catch (Exception e) {
            return Result.error(500, "系统内部异常: " + e.getMessage());
        }

    }
}