package com.personal.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.personal.system.entity.User;
import com.personal.system.service.IUserService;
import com.personal.system.utils.JwtUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
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

    private final Map<String, String> codeCache = new ConcurrentHashMap<>();

    /**
     * 发送邮箱验证码
     */
    @PostMapping("/sendCode")
    public Map<String, Object> sendCode(@RequestBody Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        String email = params.get("email");

        if (!StringUtils.hasText(email)) {
            result.put("code", 400);
            result.put("message", "邮箱不能为空");
            return result;
        }

        String code = String.format("%06d", new Random().nextInt(999999));

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setSubject("【专属控制台】验证码");
            message.setText("您的验证码为：" + code + "，请在5分钟内输入。如非本人操作，请忽略此邮件。");
            mailSender.send(message);

            codeCache.put(email, code);

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
        String password = params.get("password"); // 此时收到的是前端发来的 SHA-256 密文
        String code = params.get("code");

        if (!StringUtils.hasText(email) || !StringUtils.hasText(password) || !StringUtils.hasText(code)) {
            result.put("code", 400);
            result.put("message", "邮箱、密码或验证码不能为空");
            return result;
        }

        String savedCode = codeCache.get(email);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        User existUser = userService.getOne(queryWrapper);

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
        // 生成不重复的随机用户名：user_ + 8位随机字母数字
        String username;
        do {
            username = "user_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
            LambdaQueryWrapper<User> uq = new LambdaQueryWrapper<>();
            uq.eq(User::getUsername, username);
            if (userService.getOne(uq) == null) break;
        } while (true);
        newUser.setUsername(username);
        newUser.setEmail(email);
        // 用邮箱前缀作为默认昵称
        newUser.setNickname(email.split("@")[0]);
        // 默认头像（dicebear 随机风格）
        newUser.setAvatar("https://api.dicebear.com/7.x/bottts/svg?seed=" + username);
        String hashedPwd = BCrypt.hashpw(password, BCrypt.gensalt());
        newUser.setPassword(hashedPwd);
        userService.save(newUser);

        codeCache.remove(email);

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
        String newPassword = params.get("newPassword"); // 此时收到的是前端发来的 SHA-256 密文
        String code = params.get("code");

        if (!StringUtils.hasText(email) || !StringUtils.hasText(newPassword) || !StringUtils.hasText(code)) {
            result.put("code", 400);
            result.put("message", "参数不完整");
            return result;
        }

        String savedCode = codeCache.get(email);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        User existUser = userService.getOne(queryWrapper);

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

        // 【核心改造】：同理，更新为加盐密文
        String hashedPwd = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        existUser.setPassword(hashedPwd);
        userService.updateById(existUser);
        codeCache.remove(email);

        result.put("code", 200);
        result.put("message", "密码重置成功");
        return result;
    }

    /**
     * 邮箱登录 (发放 7 天 Token)
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        String email = params.get("email");
        String password = params.get("password"); // 此时收到的是前端发来的 SHA-256 密文

        if (!StringUtils.hasText(email) || !StringUtils.hasText(password)) {
            result.put("code", 400);
            result.put("message", "邮箱或密码不能为空");
            return result;
        }

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        User user = userService.getOne(queryWrapper);

        // 【核心改造】：使用 BCrypt.checkpw 校验（它会自动提取盐值对比前后的 SHA-256 密文是否一致）
        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            result.put("code", 400);
            result.put("message", "邮箱或密码错误");
            return result;
        }

        // 【核心改造】：签发有效期为 7 天的真实 JWT
        String token = JwtUtils.generateToken(user.getId());

        result.put("code", 200);
        result.put("message", "登录成功");
        result.put("data", token);
        return result;
    }
}