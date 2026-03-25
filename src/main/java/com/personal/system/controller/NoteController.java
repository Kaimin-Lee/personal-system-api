package com.personal.system.controller;

import com.personal.system.common.Result;
import com.personal.system.dto.NoteDTO;
import com.personal.system.entity.Note;
import com.personal.system.service.INoteService;
import com.personal.system.utils.UserContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class NoteController {

    private final INoteService noteService;

    @GetMapping("/list")
    public Result<List<Note>> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "0") Integer isDeleted) {
        return Result.success(noteService.getMyNotes(UserContext.getUserId(), keyword, isDeleted));
    }

    @PostMapping("/save")
    public Result<Note> save(@Valid @RequestBody NoteDTO.SaveNoteDTO dto) {
        Note note = new Note();
        note.setId(dto.getId());
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        note.setFolderId(dto.getFolderId());
        note.setTags(dto.getTags());
        noteService.saveNote(note, UserContext.getUserId());
        return Result.success("保存成功", note);
    }

    @PostMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        noteService.softDeleteNote(id, UserContext.getUserId());
        return Result.success("已移入回收站", null);
    }

    @PostMapping("/hardDelete/{id}")
    public Result<Void> hardDelete(@PathVariable Long id) {
        noteService.hardDeleteNote(id, UserContext.getUserId());
        return Result.success("已彻底销毁", null);
    }

    @PostMapping("/recover/{id}")
    public Result<Void> recover(@PathVariable Long id) {
        noteService.recoverNote(id, UserContext.getUserId());
        return Result.success("档案已恢复", null);
    }

    @PostMapping("/pin/{id}")
    public Result<Void> togglePin(@PathVariable Long id) {
        noteService.togglePin(id, UserContext.getUserId());
        return Result.success("置顶状态已更新", null);
    }
}
