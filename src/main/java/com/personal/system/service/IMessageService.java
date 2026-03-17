package com.personal.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.personal.system.entity.Message;
import java.util.List;

public interface IMessageService extends IService<Message> {

    // 获取管理员的会话列表
    List<Long> getSessionUserIds();

    // 获取聊天历史记录
    List<Message> getHistory(Long myId, Long targetId);

    // 发送消息
    void sendMessage(Message msg, Long myId);

    // 查询是否有未读消息
    boolean hasUnread(Long myId);

    // 将某人的消息标记为已读
    void markAsRead(Long myId, Long targetId);
}