package com.personal.system.service;

import com.personal.system.entity.Note;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 学习笔记表 服务类
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
public interface INoteService extends IService<Note> {

    List<Note> getMyNotes(Long userId, String keyword);

    void saveNote(Note note, Long userId);

    void deleteNote(Long id, Long userId);

}
