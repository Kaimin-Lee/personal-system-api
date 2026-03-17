package com.personal.system.controller;

import com.personal.system.common.Result;
import com.personal.system.entity.Shortcut;
import com.personal.system.service.IShortcutService;
import com.personal.system.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shortcut")
public class ShortcutController {

    @Autowired
    private IShortcutService shortcutService;

    @GetMapping("/list")
    public Result<List<Shortcut>> getList() {
        return Result.success(shortcutService.getMyShortcuts(UserContext.getUserId()));
    }

    @PostMapping("/add")
    public Result<Void> add(@RequestBody Shortcut shortcut) {
        shortcutService.addShortcut(shortcut, UserContext.getUserId());
        return Result.success("添加成功", null);
    }

    @PutMapping("/update")
    public Result<Void> update(@RequestBody Shortcut shortcut) {
        shortcutService.updateShortcut(shortcut, UserContext.getUserId());
        return Result.success("修改成功", null);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        shortcutService.deleteShortcut(id, UserContext.getUserId());
        return Result.success("删除成功", null);
    }
}