package com.personal.system.controller;

import com.personal.system.common.Result;
import com.personal.system.dto.ShortcutDTO;
import com.personal.system.entity.Shortcut;
import com.personal.system.service.IShortcutService;
import com.personal.system.utils.UserContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shortcut")
@RequiredArgsConstructor
public class ShortcutController {

    private final IShortcutService shortcutService;

    @GetMapping("/list")
    public Result<List<Shortcut>> getList() {
        return Result.success(shortcutService.getMyShortcuts(UserContext.getUserId()));
    }

    @PostMapping("/add")
    public Result<Void> add(@Valid @RequestBody ShortcutDTO.SaveShortcutDTO dto) {
        Shortcut shortcut = new Shortcut();
        shortcut.setSiteName(dto.getSiteName());
        shortcut.setSiteUrl(dto.getSiteUrl());
        shortcut.setIconUrl(dto.getIconUrl());
        shortcut.setCategory(dto.getCategory());
        shortcut.setSortOrder(dto.getSortOrder());
        shortcutService.addShortcut(shortcut, UserContext.getUserId());
        return Result.success("添加成功", null);
    }

    @PutMapping("/update")
    public Result<Void> update(@Valid @RequestBody ShortcutDTO.SaveShortcutDTO dto) {
        Shortcut shortcut = new Shortcut();
        shortcut.setId(dto.getId());
        shortcut.setSiteName(dto.getSiteName());
        shortcut.setSiteUrl(dto.getSiteUrl());
        shortcut.setIconUrl(dto.getIconUrl());
        shortcut.setCategory(dto.getCategory());
        shortcut.setSortOrder(dto.getSortOrder());
        shortcutService.updateShortcut(shortcut, UserContext.getUserId());
        return Result.success("修改成功", null);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        shortcutService.deleteShortcut(id, UserContext.getUserId());
        return Result.success("删除成功", null);
    }
}
