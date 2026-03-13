package com.personal.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personal.system.entity.Task;
import com.personal.system.mapper.TaskMapper;
import com.personal.system.service.ITaskService;
import com.personal.system.utils.UserContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

    @Override
    public List<Task> getMyTaskList() {
        // 业务逻辑：查本人的任务，按优先级和时间降序
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getUserId, UserContext.getUserId())
                .orderByDesc(Task::getPriority)
                .orderByDesc(Task::getCreateTime);
        return this.list(wrapper);
    }

    @Override
    public void addMyTask(Task task) {
        task.setUserId(UserContext.getUserId());
        if (task.getStatus() == null) {
            task.setStatus(0); // 默认待办
        }
        this.save(task);
    }

    @Override
    public boolean updateMyTask(Task task) {
        // 安全校验逻辑
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getId, task.getId()).eq(Task::getUserId, UserContext.getUserId());
        if (this.count(wrapper) > 0) {
            return this.updateById(task);
        }
        return false;
    }

    @Override
    public boolean updateMyTaskStatus(Task task) {
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getId, task.getId()).eq(Task::getUserId, UserContext.getUserId());
        Task existTask = this.getOne(wrapper);
        if (existTask != null) {
            existTask.setStatus(task.getStatus());
            return this.updateById(existTask);
        }
        return false;
    }

    @Override
    public boolean deleteMyTask(Long id) {
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getId, id).eq(Task::getUserId, UserContext.getUserId());
        return this.remove(wrapper);
    }
}