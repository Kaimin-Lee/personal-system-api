package com.personal.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.personal.system.common.BusinessException;
import com.personal.system.dto.AuthDTO;
import com.personal.system.entity.User;
import com.personal.system.mapper.UserMapper;
import com.personal.system.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personal.system.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    // 简单缓存，生产环境建议改用 Redis 并设置 TTL
    private final ConcurrentHashMap<String, String> codeCache = new ConcurrentHashMap<>();

    @Override
    public void sendEmailCode(String email) {
        if (!StringUtils.hasText(email)) {
            throw new BusinessException("邮箱不能为空");
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
        } catch (Exception e) {
            throw new BusinessException(500, "邮件发送失败：" + e.getMessage());
        }
    }

    @Override
    public void register(AuthDTO.RegisterDTO dto) {
        if (!StringUtils.hasText(dto.getEmail()) || !StringUtils.hasText(dto.getPassword()) || !StringUtils.hasText(dto.getCode())) {
            throw new BusinessException("邮箱、密码或验证码不能为空");
        }
        if (!dto.getCode().equals(codeCache.get(dto.getEmail()))) {
            throw new BusinessException("验证码错误或已失效");
        }
        if (this.count(new LambdaQueryWrapper<User>().eq(User::getEmail, dto.getEmail())) > 0) {
            throw new BusinessException("该邮箱已被注册");
        }

        User newUser = new User();
        String username = "user_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        newUser.setUsername(username);
        newUser.setEmail(dto.getEmail());
        newUser.setNickname(dto.getEmail().split("@")[0]);
        newUser.setAvatar("https://api.dicebear.com/7.x/bottts/svg?seed=" + username);
        newUser.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));

        this.save(newUser);
        codeCache.remove(dto.getEmail());
    }

    @Override
    public void resetPassword(AuthDTO.ResetPwdDTO dto) {
        if (!StringUtils.hasText(dto.getEmail()) || !StringUtils.hasText(dto.getNewPassword()) || !StringUtils.hasText(dto.getCode())) {
            throw new BusinessException("参数不完整");
        }
        if (!dto.getCode().equals(codeCache.get(dto.getEmail()))) {
            throw new BusinessException("验证码错误或已失效");
        }

        User existUser = this.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, dto.getEmail()));
        if (existUser == null) {
            throw new BusinessException("该邮箱未注册");
        }

        existUser.setPassword(BCrypt.hashpw(dto.getNewPassword(), BCrypt.gensalt()));
        this.updateById(existUser);
        codeCache.remove(dto.getEmail());
    }

    @Override
    public String login(AuthDTO.LoginDTO dto) {
        if (!StringUtils.hasText(dto.getEmail()) || !StringUtils.hasText(dto.getPassword())) {
            throw new BusinessException("邮箱或密码不能为空");
        }

        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, dto.getEmail()));
        if (user == null || !BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
            throw new BusinessException("邮箱或密码错误");
        }

        return JwtUtils.generateToken(user.getId());
    }
}