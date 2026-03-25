package com.personal.system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class MessageDTO {

    @Data
    public static class SendMessageDTO {
        @NotNull(message = "接收人ID不能为空")
        private Long receiverId;

        @NotBlank(message = "消息内容不能为空")
        private String content;
    }
}
