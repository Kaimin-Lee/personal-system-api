package com.personal.system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;

public class CountdownDTO {

    @Data
    public static class SaveCountdownDTO {
        private Long id;

        @NotBlank(message = "事件名称不能为空")
        private String eventName;

        @NotNull(message = "目标日期不能为空")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate targetDate;

        private Boolean isPinned;
    }
}
