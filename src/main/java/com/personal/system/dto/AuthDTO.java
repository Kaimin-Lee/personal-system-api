package com.personal.system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class AuthDTO {

    @Data
    public static class SendCodeDTO {
        @NotBlank(message = "邮箱不能为空")
        @Email(message = "邮箱格式不正确")
        private String email;
    }

    @Data
    public static class RegisterDTO {
        @NotBlank(message = "邮箱不能为空")
        @Email(message = "邮箱格式不正确")
        private String email;

        @NotBlank(message = "密码不能为空")
        @Size(min = 6, max = 32, message = "密码长度须在6-32位之间")
        private String password;

        @NotBlank(message = "验证码不能为空")
        private String code;
    }

    @Data
    public static class LoginDTO {
        @NotBlank(message = "邮箱不能为空")
        @Email(message = "邮箱格式不正确")
        private String email;

        @NotBlank(message = "密码不能为空")
        private String password;
    }

    @Data
    public static class ResetPwdDTO {
        @NotBlank(message = "邮箱不能为空")
        @Email(message = "邮箱格式不正确")
        private String email;

        @NotBlank(message = "新密码不能为空")
        @Size(min = 6, max = 32, message = "密码长度须在6-32位之间")
        private String newPassword;

        @NotBlank(message = "验证码不能为空")
        private String code;
    }
}
