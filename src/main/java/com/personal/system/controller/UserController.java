package com.personal.system.controller;

import com.personal.system.common.BusinessException;
import com.personal.system.common.Result;
import com.personal.system.entity.User;
import com.personal.system.service.IUserService;
import com.personal.system.utils.UserContext;
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
public class UserController {

    private final IUserService userService;

    @Value("${upload.avatar-dir:uploads/avatars}")
    private String avatarDir;

    @Value("${upload.base-url:http://localhost:40910}")
    private String baseUrl;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

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
    public Result<Void> updateProfile(@RequestBody Map<String, String> params) {
        User user = userService.getById(UserContext.getUserId());
        if (user == null) throw new BusinessException("用户不存在");
        String nickname = params.get("nickname");
        if (StringUtils.hasText(nickname)) user.setNickname(nickname);
        String avatar = params.get("avatar");
        if (StringUtils.hasText(avatar)) user.setAvatar(avatar);
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

    @PostMapping("/password")
    public Result<Void> changePassword(@RequestBody Map<String, String> params) {
        User user = userService.getById(UserContext.getUserId());
        if (user == null) throw new BusinessException("用户不存在");
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        if (!StringUtils.hasText(oldPassword) || !StringUtils.hasText(newPassword))
            throw new BusinessException("参数不完整");
        if (!BCrypt.checkpw(oldPassword, user.getPassword()))
            throw new BusinessException("原密码错误");
        user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        userService.updateById(user);
        return Result.success("密码修改成功", null);
    }
}
