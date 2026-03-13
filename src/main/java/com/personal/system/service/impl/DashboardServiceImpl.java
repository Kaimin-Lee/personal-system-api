package com.personal.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.personal.system.entity.Countdown;
import com.personal.system.entity.Task;
import com.personal.system.service.ICountdownService;
import com.personal.system.service.IDashboardService;
import com.personal.system.service.ITaskService;
import com.personal.system.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements IDashboardService {

    @Autowired
    private ITaskService taskService;

    @Autowired
    private ICountdownService countdownService;

    @Override
    public Map<String, Object> getDashboardData(String sortBy, String sortOrder) {
        Long userId = UserContext.getUserId();

        // 1. 查任务逻辑
        LambdaQueryWrapper<Task> taskWrapper = new LambdaQueryWrapper<>();
        taskWrapper.eq(Task::getUserId, userId).in(Task::getStatus, 0, 1);

        boolean isAsc = "asc".equalsIgnoreCase(sortOrder);
        if ("deadline".equals(sortBy)) {
            if (isAsc) {
                taskWrapper.last("ORDER BY deadline IS NULL, deadline ASC LIMIT 5");
            } else {
                taskWrapper.orderByDesc(Task::getDeadline).last("LIMIT 5");
            }
        } else {
            if (isAsc) {
                taskWrapper.orderByAsc(Task::getPriority).orderByAsc(Task::getCreateTime).last("LIMIT 5");
            } else {
                taskWrapper.orderByDesc(Task::getPriority).orderByDesc(Task::getCreateTime).last("LIMIT 5");
            }
        }
        List<Task> activeTasks = taskService.list(taskWrapper);

        // 2. 查倒数日逻辑
        LambdaQueryWrapper<Countdown> countdownWrapper = new LambdaQueryWrapper<>();
        countdownWrapper.eq(Countdown::getUserId, userId)
                .eq(Countdown::getIsPinned, 1)
                .orderByAsc(Countdown::getTargetDate)
                .last("LIMIT 5");
        List<Countdown> pinnedCountdowns = countdownService.list(countdownWrapper);

        // 3. 组装数据并返回
        Map<String, Object> data = new HashMap<>();
        data.put("tasks", activeTasks);
        data.put("countdowns", pinnedCountdowns);

        return data;
    }
}