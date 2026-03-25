package com.personal.system.controller;

import com.personal.system.common.Result;
import com.personal.system.dto.TaskDTO;
import com.personal.system.dto.UpdateSortDTO;
import com.personal.system.entity.Task;
import com.personal.system.service.ITaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final ITaskService taskService;

    @GetMapping("/list")
    public Result<List<Task>> getList() {
        return Result.success(taskService.getMyTaskList());
    }

    @PostMapping("/add")
    public Result<Void> add(@Valid @RequestBody TaskDTO.SaveTaskDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setDeadline(dto.getDeadline());
        taskService.addMyTask(task);
        return Result.success("新增成功", null);
    }

    @PutMapping("/update")
    public Result<Void> update(@Valid @RequestBody TaskDTO.SaveTaskDTO dto) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setDeadline(dto.getDeadline());
        if (taskService.updateMyTask(task)) {
            return Result.success("修改成功", null);
        }
        return Result.error("无权操作或任务不存在");
    }

    @PutMapping("/updateStatus")
    public Result<Void> updateStatus(@Valid @RequestBody TaskDTO.UpdateStatusDTO dto) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setStatus(dto.getStatus());
        task.setSortOrder(dto.getSortOrder());
        if (taskService.updateMyTaskStatus(task)) {
            return Result.success("状态已更新", null);
        }
        return Result.error("更新失败");
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        if (taskService.deleteMyTask(id)) {
            return Result.success("删除成功", null);
        }
        return Result.error("无权操作或任务不存在");
    }

    @PutMapping("/updateSort")
    public Result<Void> updateSort(@RequestBody UpdateSortDTO dto) {
        if (taskService.updateTaskSort(dto.getStatus(), dto.getSortedIds())) {
            return Result.success("排序已更新", null);
        }
        return Result.error("更新排序失败");
    }
}
