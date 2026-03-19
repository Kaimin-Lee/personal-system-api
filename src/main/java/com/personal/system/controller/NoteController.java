package com.personal.system.controller;

import com.personal.system.common.Result;
import com.personal.system.entity.Note;
import com.personal.system.service.INoteService;
import com.personal.system.utils.UserContext;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    private final INoteService noteService;

    public NoteController(INoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/list")
    public Result<List<Note>> list(@RequestParam(required = false) String keyword) {
        return Result.success(noteService.getMyNotes(UserContext.getUserId(), keyword));
    }

    @PostMapping("/save")
    public Result<Note> save(@RequestBody Note note) {
        noteService.saveNote(note, UserContext.getUserId());
        return Result.success("保存成功", note);
    }

    @PostMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        noteService.deleteNote(id, UserContext.getUserId());
        return Result.success("删除成功", null);
    }
}