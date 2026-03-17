package com.personal.system.service;

import com.personal.system.entity.Shortcut;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 快捷导航网址表 服务类
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
public interface IShortcutService extends IService<Shortcut> {

    List<Shortcut> getMyShortcuts(Long userId);

    void addShortcut(Shortcut shortcut, Long userId);

    void updateShortcut(Shortcut shortcut, Long userId);

    void deleteShortcut(Long id, Long userId);
}
