package com.personal.system.controller;

import com.personal.system.common.Result;
import com.personal.system.dto.AuthDTO;
import com.personal.system.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IUserService userService;

    @PostMapping("/sendCode")
    public Result<Void> sendCode(@RequestBody AuthDTO.SendCodeDTO dto) {
        userService.sendEmailCode(dto.getEmail());
        return Result.success("验证码发送成功", null);
    }

    @PostMapping("/register")
    public Result<Void> register(@RequestBody AuthDTO.RegisterDTO dto) {
        userService.register(dto);
        return Result.success("注册成功", null);
    }

    @PostMapping("/resetPwd")
    public Result<Void> resetPwd(@RequestBody AuthDTO.ResetPwdDTO dto) {
        userService.resetPassword(dto);
        return Result.success("密码重置成功", null);
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody AuthDTO.LoginDTO dto) {
        String token = userService.login(dto);
        return Result.success("登录成功", token);
    }
}