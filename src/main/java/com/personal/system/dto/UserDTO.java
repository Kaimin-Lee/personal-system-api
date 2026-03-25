package com.personal.system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class UserDTO {

    @Data
    public static class UpdateProfileDTO {
        private String nickname;
        private String avatar;
    }

    @Data
    public static class ChangePasswordDTO {
        @NotBlank(message = "原密码不能为空")
        private String oldPassword;

        @NotBlank(message = "新密码不能为空")
        @Size(min = 6, max = 32, message = "密码长度须在6-32位之间")
        private String newPassword;
    }
}
