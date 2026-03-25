package com.personal.system.dto;

import lombok.Data;

public class AuthDTO {
    @Data
    public static class SendCodeDTO {
        private String email;
    }

    @Data
    public static class RegisterDTO {
        private String email;
        private String password;
        private String code;
    }

    @Data
    public static class LoginDTO {
        private String email;
        private String password;
    }

    @Data
    public static class ResetPwdDTO {
        private String email;
        private String newPassword;
        private String code;
    }
}