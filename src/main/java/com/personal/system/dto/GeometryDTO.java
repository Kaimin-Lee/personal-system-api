package com.personal.system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class GeometryDTO {

    @Data
    public static class AddHistoryDTO {
        @NotBlank(message = "图形名称不能为空")
        private String shapeName;

        @NotBlank(message = "参数不能为空")
        private String params;

        @NotBlank(message = "计算结果不能为空")
        private String result;
    }
}
