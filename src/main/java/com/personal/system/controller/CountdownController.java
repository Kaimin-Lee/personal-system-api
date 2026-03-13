package com.personal.system.controller;

import com.personal.system.common.Result;
import com.personal.system.entity.Countdown;
import com.personal.system.service.ICountdownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/countdown")
public class CountdownController {

    @Autowired
    private ICountdownService countdownService;

    @GetMapping("/list")
    public Result<List<Countdown>> list() {
        return Result.success(countdownService.getMyCountdownList());
    }

    @PostMapping("/add")
    public Result<Void> add(@RequestBody Countdown countdown) {
        countdownService.addMyCountdown(countdown);
        return Result.success("添加成功", null);
    }

    @PutMapping("/update")
    public Result<Void> update(@RequestBody Countdown countdown) {
        return countdownService.updateMyCountdown(countdown) ?
                Result.success("修改成功", null) : Result.error("操作失败");
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        return countdownService.deleteMyCountdown(id) ?
                Result.success("删除成功", null) : Result.error("删除失败");
    }
}