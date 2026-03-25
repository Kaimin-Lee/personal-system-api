package com.personal.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.personal.system.entity.Task;

import java.util.List;

public interface ITaskService extends IService<Task> {

    // 获取当前登录用户的所有任务
    List<Task> getMyTaskList();

    // 新增任务
    void addMyTask(Task task);

    // 修改任务详情 (返回布尔值代表是否成功)
    boolean updateMyTask(Task task);

    // 拖拽修改任务状态
    boolean updateMyTaskStatus(Task task);

    // 删除任务
    boolean deleteMyTask(Long id);

    // 批量更新任务排序
    boolean updateTaskSort(Integer status, List<Long> sortedIds);
}