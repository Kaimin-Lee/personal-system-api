package com.personal.system.service;

import com.personal.system.dto.AuthDTO;
import com.personal.system.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IUserService extends IService<User> {
    void sendEmailCode(String email);
    void register(AuthDTO.RegisterDTO dto);
    void resetPassword(AuthDTO.ResetPwdDTO dto);
    String login(AuthDTO.LoginDTO dto);
}