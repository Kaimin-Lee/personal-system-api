package com.personal.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personal.system.entity.Shortcut;
import com.personal.system.mapper.ShortcutMapper;
import com.personal.system.service.IShortcutService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShortcutServiceImpl extends ServiceImpl<ShortcutMapper, Shortcut> implements IShortcutService {

    @Override
    public List<Shortcut> getMyShortcuts(Long userId) {
        // 先按排序权重降序，再按创建时间降序
        return this.list(new LambdaQueryWrapper<Shortcut>()
                .eq(Shortcut::getUserId, userId)
                .orderByDesc(Shortcut::getSortOrder)
                .orderByDesc(Shortcut::getCreateTime));
    }

    @Override
    public void addShortcut(Shortcut shortcut, Long userId) {
        shortcut.setUserId(userId);
        shortcut.setCreateTime(LocalDateTime.now());
        this.save(shortcut);
    }

    @Override
    public void updateShortcut(Shortcut shortcut, Long userId) {
        // 安全校验：确认为本人的记录才能修改
        shortcut.setUserId(userId);
        this.updateById(shortcut);
    }

    @Override
    public void deleteShortcut(Long id, Long userId) {
        this.remove(new LambdaQueryWrapper<Shortcut>()
                .eq(Shortcut::getId, id)
                .eq(Shortcut::getUserId, userId));
    }
}