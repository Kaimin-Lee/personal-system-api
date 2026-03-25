package com.personal.system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

public class TaskDTO {

    @Data
    public static class SaveTaskDTO {
        private Long id;

        @NotBlank(message = "任务名称不能为空")
        private String title;

        private String description;

        private Integer status;

        private Integer priority;

        private LocalDateTime deadline;
    }

    @Data
    public static class UpdateStatusDTO {
        @NotNull(message = "任务ID不能为空")
        private Long id;

        @NotNull(message = "状态不能为空")
        private Integer status;

        private Integer sortOrder;
    }
}
