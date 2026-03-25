package com.personal.system.controller;

import com.personal.system.common.Result;
import com.personal.system.dto.MessageDTO;
import com.personal.system.entity.Message;
import com.personal.system.service.IMessageService;
import com.personal.system.utils.UserContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {

    private final IMessageService messageService;

    @GetMapping("/sessions")
    public Result<List<Long>> getSessions() {
        Long currentUserId = UserContext.getUserId();
        if (currentUserId != 1L) {
            return Result.error("无权限");
        }
        return Result.success(messageService.getSessionUserIds());
    }

    @GetMapping("/history")
    public Result<List<Message>> getHistory(@RequestParam Long targetId) {
        Long myId = UserContext.getUserId();
        if (myId != 1L) {
            targetId = 1L;
        }
        return Result.success(messageService.getHistory(myId, targetId));
    }

    @PostMapping("/send")
    public Result<Void> send(@Valid @RequestBody MessageDTO.SendMessageDTO dto) {
        Message msg = new Message();
        msg.setReceiverId(dto.getReceiverId());
        msg.setContent(dto.getContent());
        messageService.sendMessage(msg, UserContext.getUserId());
        return Result.success("发送成功", null);
    }

    @GetMapping("/unread")
    public Result<Boolean> checkUnread() {
        return Result.success(messageService.hasUnread(UserContext.getUserId()));
    }

    @PostMapping("/read")
    public Result<Void> markAsRead(@RequestParam(required = false) String targetId) {
        Long tId = null;
        if (targetId != null && !"null".equals(targetId) && !"undefined".equals(targetId) && !targetId.trim().isEmpty()) {
            tId = Long.valueOf(targetId);
        }
        messageService.markAsRead(UserContext.getUserId(), tId);
        return Result.success("已读", null);
    }
}
