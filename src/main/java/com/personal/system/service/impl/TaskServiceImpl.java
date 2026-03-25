package com.personal.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personal.system.entity.Task;
import com.personal.system.mapper.TaskMapper;
import com.personal.system.service.ITaskService;
import com.personal.system.utils.UserContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(rollbackFor = Exception.class) // 开启事务，保证批量更新要么全成功要么全失败
    public boolean updateTaskSort(Integer status, List<Long> sortedIds) {
        if (sortedIds == null || sortedIds.isEmpty()) {
            return true;
        }

        Long userId = UserContext.getUserId();

        // 遍历前端传来的 ID 数组，数组的 index 索引就是该任务最新的排序值
        for (int i = 0; i < sortedIds.size(); i++) {
            Long taskId = sortedIds.get(i);

            // 使用 UpdateWrapper 增加 userId 校验，防止越权篡改别人任务的排序
            LambdaUpdateWrapper<Task> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Task::getId, taskId)
                    .eq(Task::getUserId, userId)
                    .set(Task::getSortOrder, i) // sort_order 从 0 开始递增
                    .set(Task::getStatus, status); // 顺便把状态也同步了，双重保险

            this.update(updateWrapper);
        }

        return true;
    }
}