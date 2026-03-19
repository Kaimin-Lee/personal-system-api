package com.personal.system.service;

import com.personal.system.entity.Note;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

public interface INoteService extends IService<Note> {

    // 🌟 修改：支持回收站过滤和全文检索
    List<Note> getMyNotes(Long userId, String keyword, Integer isDeleted);

    void saveNote(Note note, Long userId);

    void softDeleteNote(Long id, Long userId);

    void hardDeleteNote(Long id, Long userId);

    void recoverNote(Long id, Long userId);

    void togglePin(Long id, Long userId);
}