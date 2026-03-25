package com.personal.system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class MemoDTO {

    @Data
    public static class SaveMemoDTO {
        private Long id;

        @NotBlank(message = "备忘录内容不能为空")
        private String content;

        private String bgColor;
    }
}
