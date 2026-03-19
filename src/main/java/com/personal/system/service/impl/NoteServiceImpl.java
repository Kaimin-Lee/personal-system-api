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
    public List<Note> getMyNotes(Long userId, String keyword, Integer isDeleted) {
        LambdaQueryWrapper<Note> wrapper = new LambdaQueryWrapper<Note>()
                .eq(Note::getUserId, userId)
                .eq(Note::getIsDeleted, isDeleted != null ? isDeleted : 0); // 默认只查未删除的

        if (keyword != null && !keyword.trim().isEmpty()) {
            wrapper.and(w -> w.like(Note::getTitle, keyword)
                    .or().like(Note::getTags, keyword)
                    .or().like(Note::getContent, keyword));
        }

        wrapper.orderByDesc(Note::getIsPinned).orderByDesc(Note::getUpdateTime);

        return this.list(wrapper);
    }

    @Override
    public void saveNote(Note note, Long userId) {
        note.setUserId(userId);
        if (note.getId() == null) {
            if (note.getFolderId() == null) note.setFolderId(0L);
            note.setIsPinned(0);
            note.setIsDeleted(0);
            this.save(note);
        } else {
            this.updateById(note);
        }
    }

    @Override
    public void softDeleteNote(Long id, Long userId) {
        Note note = this.getOne(new LambdaQueryWrapper<Note>().eq(Note::getId, id).eq(Note::getUserId, userId));
        if (note == null) throw new BusinessException("笔记不存在或无权限");
        note.setIsDeleted(1);
        note.setIsPinned(0); // 放入回收站时取消置顶
        this.updateById(note);
    }

    @Override
    public void hardDeleteNote(Long id, Long userId) {
        this.remove(new LambdaQueryWrapper<Note>().eq(Note::getId, id).eq(Note::getUserId, userId));
    }

    @Override
    public void recoverNote(Long id, Long userId) {
        Note note = this.getOne(new LambdaQueryWrapper<Note>().eq(Note::getId, id).eq(Note::getUserId, userId));
        if (note == null) throw new BusinessException("笔记不存在或无权限");
        note.setIsDeleted(0);
        this.updateById(note);
    }

    @Override
    public void togglePin(Long id, Long userId) {
        Note note = this.getOne(new LambdaQueryWrapper<Note>().eq(Note::getId, id).eq(Note::getUserId, userId));
        if (note == null) throw new BusinessException("笔记不存在或无权限");
        note.setIsPinned(note.getIsPinned() == null || note.getIsPinned() == 0 ? 1 : 0);
        this.updateById(note);
    }
}