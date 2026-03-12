package com.personal.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.personal.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.personal.system.entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    // 简易内存缓存，用于存储邮箱验证码 (生产环境建议用Redis)
    private final Map<String, String> codeCache = new ConcurrentHashMap<>();

    /**
     * 发送邮箱验证码
     */
    @PostMapping("/sendCode")
    public Map<String, Object> sendCode(@RequestBody Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        String email = params.get("email");

        // 1. 先校验参数
        if (!StringUtils.hasText(email)) {
            result.put("code", 400);
            result.put("message", "邮箱不能为空");
            return result;
        }

        // 2. 然后去查 (这里不需要查数据库，直接生成验证码)
        String code = String.format("%06d", new Random().nextInt(999999));

        // 3. 最后统一处理：发邮件并缓存
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setSubject("【专属控制台】验证码");
            message.setText("您的验证码为：" + code + "，请在5分钟内输入。如非本人操作，请忽略此邮件。");
            mailSender.send(message);

            codeCache.put(email, code); // 存入缓存

            result.put("code", 200);
            result.put("message", "验证码发送成功");
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "邮件发送失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        String email = params.get("email");
        String password = params.get("password");
        String code = params.get("code");

        // 1. 先校验参数
        if (!StringUtils.hasText(email) || !StringUtils.hasText(password) || !StringUtils.hasText(code)) {
            result.put("code", 400);
            result.put("message", "邮箱、密码或验证码不能为空");
            return result;
        }

        // 2. 然后去查
        String savedCode = codeCache.get(email);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        User existUser = userService.getOne(queryWrapper);

        // 3. 最后统一处理
        if (!code.equals(savedCode)) {
            result.put("code", 400);
            result.put("message", "验证码错误或已失效");
            return result;
        }
        if (existUser != null) {
            result.put("code", 400);
            result.put("message", "该邮箱已被注册");
            return result;
        }

        User newUser = new User();
        newUser.setUsername(email); // 默认使用邮箱作为账号名
        newUser.setEmail(email);
        newUser.setPassword(password); // 提示：后续集成安全框架时，这里需要加密
        userService.save(newUser);

        codeCache.remove(email); // 注册成功后清除验证码

        result.put("code", 200);
        result.put("message", "注册成功");
        return result;
    }

    /**
     * 密码修改/重置
     */
    @PostMapping("/resetPwd")
    public Map<String, Object> resetPwd(@RequestBody Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        String email = params.get("email");
        String newPassword = params.get("newPassword");
        String code = params.get("code");

        // 1. 先校验参数
        if (!StringUtils.hasText(email) || !StringUtils.hasText(newPassword) || !StringUtils.hasText(code)) {
            result.put("code", 400);
            result.put("message", "参数不完整");
            return result;
        }

        // 2. 然后去查
        String savedCode = codeCache.get(email);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        User existUser = userService.getOne(queryWrapper);

        // 3. 最后统一处理
        if (!code.equals(savedCode)) {
            result.put("code", 400);
            result.put("message", "验证码错误或已失效");
            return result;
        }
        if (existUser == null) {
            result.put("code", 400);
            result.put("message", "该邮箱未注册");
            return result;
        }

        existUser.setPassword(newPassword);
        userService.updateById(existUser);
        codeCache.remove(email);

        result.put("code", 200);
        result.put("message", "密码重置成功");
        return result;
    }

    /**
     * 邮箱登录 (暂无Token鉴权版)
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        String email = params.get("email");
        String password = params.get("password");

        // 1. 先校验参数
        if (!StringUtils.hasText(email) || !StringUtils.hasText(password)) {
            result.put("code", 400);
            result.put("message", "邮箱或密码不能为空");
            return result;
        }

        // 2. 然后去查
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        User user = userService.getOne(queryWrapper);

        // 3. 最后统一处理
        if (user == null || !user.getPassword().equals(password)) {
            result.put("code", 400);
            result.put("message", "邮箱或密码错误");
            return result;
        }

        result.put("code", 200);
        result.put("message", "登录成功");
        result.put("data", "mock-token-" + user.getId()); // 预留后续JWT的位置
        return result;
    }
}