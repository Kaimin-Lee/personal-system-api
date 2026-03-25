package com.personal.system.dto;

import lombok.Data;

public class NoteDTO {

    @Data
    public static class SaveNoteDTO {
        private Long id;
        private String title;
        private String content;
        private Long folderId;
        private String tags;
    }
}
