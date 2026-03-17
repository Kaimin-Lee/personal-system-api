package com.personal.system.controller;

import com.personal.system.common.Result;
import com.personal.system.entity.Message;
import com.personal.system.service.IMessageService;
import com.personal.system.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private IMessageService messageService;

    // 1. 获取会话列表 (仅 userId=1 可用)
    @GetMapping("/sessions")
    public Result<List<Long>> getSessions() {
        Long currentUserId = UserContext.getUserId();
        if (currentUserId != 1L) {
            return Result.error("无权限");
        }
        return Result.success(messageService.getSessionUserIds());
    }

    // 2. 获取与某个用户的历史聊天记录
    @GetMapping("/history")
    public Result<List<Message>> getHistory(@RequestParam Long targetId) {
        Long myId = UserContext.getUserId();
        // 如果是普通用户，强制将 targetId 设为 1（防止越权接口刷数据）
        if (myId != 1L) {
            targetId = 1L;
        }
        return Result.success(messageService.getHistory(myId, targetId));
    }

    // 3. 发送消息
    @PostMapping("/send")
    public Result<Void> send(@RequestBody Message msg) {
        Long myId = UserContext.getUserId();
        messageService.sendMessage(msg, myId);
        return Result.success("发送成功", null);
    }

    // 4. 查询当前用户是否有未读消息
    @GetMapping("/unread")
    public Result<Boolean> checkUnread() {
        return Result.success(messageService.hasUnread(UserContext.getUserId()));
    }

    // 5. 标记消息为已读
    @PostMapping("/read")
    public Result<Void> markAsRead(@RequestParam(required = false) Long targetId) {
        messageService.markAsRead(UserContext.getUserId(), targetId);
        return Result.success("已读", null);
    }
}