package com.personal.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.personal.system.entity.Countdown;
import java.util.List;

public interface ICountdownService extends IService<Countdown> {
    // 获取当前用户的所有倒数日（置顶在前，日期最近在后）
    List<Countdown> getMyCountdownList();

    // 新增倒数日
    void addMyCountdown(Countdown countdown);

    // 安全修改
    boolean updateMyCountdown(Countdown countdown);

    // 安全删除
    boolean deleteMyCountdown(Long id);
}