package com.personal.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personal.system.entity.Message;
import com.personal.system.mapper.MessageMapper;
import com.personal.system.service.IMessageService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Override
    public List<Long> getSessionUserIds() {
        // 直接调用 Mapper 中手写的 SQL
        return this.baseMapper.getSessionUserIds();
    }

    @Override
    public List<Message> getHistory(Long myId, Long targetId) {
        return this.list(new LambdaQueryWrapper<Message>()
                .and(w -> w.eq(Message::getSenderId, myId).eq(Message::getReceiverId, targetId))
                .or(w -> w.eq(Message::getSenderId, targetId).eq(Message::getReceiverId, myId))
                .orderByAsc(Message::getCreateTime));
    }

    @Override
    public void sendMessage(Message msg, Long myId) {
        msg.setSenderId(myId);
        // 如果是普通用户发消息，强制接收者为管理员(1L)
        if (myId != 1L) {
            msg.setReceiverId(1L);
        }
        msg.setCreateTime(LocalDateTime.now());
        msg.setIsRead(0);

        this.save(msg);
    }

    @Override
    public boolean hasUnread(Long myId) {
        // 只要有 1 条发给我的、且 is_read 为 0 的消息，就提示红点
        return this.count(new LambdaQueryWrapper<Message>()
                .eq(Message::getReceiverId, myId)
                .eq(Message::getIsRead, 0)) > 0;
    }

    @Override
    public void markAsRead(Long myId, Long targetId) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<Message>()
                .eq(Message::getReceiverId, myId)
                .eq(Message::getIsRead, 0);
        if (targetId != null) {
            wrapper.eq(Message::getSenderId, targetId);
        }

        Message updateObj = new Message();
        updateObj.setIsRead(1);
        this.update(updateObj, wrapper);
    }
}