package com.personal.system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class ShortcutDTO {

    @Data
    public static class SaveShortcutDTO {
        private Long id;

        @NotBlank(message = "网站名称不能为空")
        private String siteName;

        @NotBlank(message = "网站链接不能为空")
        private String siteUrl;

        private String iconUrl;

        private String category;

        private Integer sortOrder;
    }
}
