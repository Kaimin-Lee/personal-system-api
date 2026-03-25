package com.personal.system.controller;

import com.personal.system.common.BusinessException;
import com.personal.system.common.Result;
import com.personal.system.dto.UserDTO;
import com.personal.system.entity.User;
import com.personal.system.service.IUserService;
import com.personal.system.utils.UserContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @Value("${upload.avatar-dir:uploads/avatars}")
    private String avatarDir;

    @Value("${upload.base-url:http://localhost:40910}")
    private String baseUrl;

    @GetMapping("/profile")
    public Result<Map<String, Object>> getProfile() {
        User user = userService.getById(UserContext.getUserId());
        if (user == null) throw new BusinessException("用户不存在");
        Map<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("username", user.getUsername());
        data.put("nickname", user.getNickname());
        data.put("email", user.getEmail());
        data.put("avatar", user.getAvatar());
        data.put("createTime", user.getCreateTime());
        return Result.success(data);
    }

    @PostMapping("/profile")
    public Result<Void> updateProfile(@RequestBody UserDTO.UpdateProfileDTO dto) {
        User user = userService.getById(UserContext.getUserId());
        if (user == null) throw new BusinessException("用户不存在");
        if (StringUtils.hasText(dto.getNickname())) user.setNickname(dto.getNickname());
        if (StringUtils.hasText(dto.getAvatar())) user.setAvatar(dto.getAvatar());
        userService.updateById(user);
        return Result.success("更新成功", null);
    }

    @PostMapping("/avatar")
    public Result<String> uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new BusinessException("文件不能为空");
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/"))
            throw new BusinessException("只支持图片格式");
        if (file.getSize() > 2 * 1024 * 1024)
            throw new BusinessException("图片不能超过 2MB");

        String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID().toString().replace("-", "") + "." + ext;

        File dir = new File(avatarDir).getAbsoluteFile();
        if (!dir.exists()) dir.mkdirs();
        file.transferTo(new File(dir, filename));

        String url = baseUrl + "/avatars/" + filename;
        User user = userService.getById(UserContext.getUserId());
        user.setAvatar(url);
        userService.updateById(user);
        return Result.success("上传成功", url);
    }

    @GetMapping("/info/{id}")
    public Result<Map<String, Object>> getUserInfo(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) throw new BusinessException("用户不存在");
        Map<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("username", user.getUsername());
        data.put("nickname", user.getNickname());
        data.put("avatar", user.getAvatar());
        return Result.success(data);
    }

    @PostMapping("/password")
    public Result<Void> changePassword(@Valid @RequestBody UserDTO.ChangePasswordDTO dto) {
        User user = userService.getById(UserContext.getUserId());
        if (user == null) throw new BusinessException("用户不存在");
        if (!BCrypt.checkpw(dto.getOldPassword(), user.getPassword()))
            throw new BusinessException("原密码错误");
        user.setPassword(BCrypt.hashpw(dto.getNewPassword(), BCrypt.gensalt()));
        userService.updateById(user);
        return Result.success("密码修改成功", null);
    }
}
