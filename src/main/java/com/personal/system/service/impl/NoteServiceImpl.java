package com.personal.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personal.system.common.BusinessException;
import com.personal.system.entity.Note;
import com.personal.system.mapper.NoteMapper;
import com.personal.system.service.INoteService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements INoteService {

    @Override
    public List<Note> getMyNotes(Long userId, String keyword) {
        LambdaQueryWrapper<Note> wrapper = new LambdaQueryWrapper<Note>().eq(Note::getUserId, userId);
        if (keyword != null && !keyword.trim().isEmpty()) {
            wrapper.like(Note::getTitle, keyword).or().like(Note::getTags, keyword);
        }
        wrapper.orderByDesc(Note::getUpdateTime);
        return this.list(wrapper);
    }

    @Override
    public void saveNote(Note note, Long userId) {
        note.setUserId(userId);
        if (note.getId() == null) {
            if (note.getFolderId() == null) note.setFolderId(0L);
            this.save(note);
        } else {
            this.updateById(note);
        }
    }

    @Override
    public void deleteNote(Long id, Long userId) {
        Note note = this.getOne(new LambdaQueryWrapper<Note>().eq(Note::getId, id).eq(Note::getUserId, userId));
        if (note == null) throw new BusinessException("笔记不存在或无权限");
        this.removeById(id);
    }
}