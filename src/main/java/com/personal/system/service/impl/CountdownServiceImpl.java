package com.personal.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personal.system.entity.Countdown;
import com.personal.system.mapper.CountdownMapper;
import com.personal.system.service.ICountdownService;
import com.personal.system.utils.UserContext;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CountdownServiceImpl extends ServiceImpl<CountdownMapper, Countdown> implements ICountdownService {

    @Override
    public List<Countdown> getMyCountdownList() {
        return this.list(new LambdaQueryWrapper<Countdown>()
                .eq(Countdown::getUserId, UserContext.getUserId())
                .orderByDesc(Countdown::getIsPinned) // 置顶优先
                .orderByAsc(Countdown::getTargetDate)); // 日期近的在前
    }

    @Override
    public void addMyCountdown(Countdown countdown) {
        countdown.setUserId(UserContext.getUserId());
        this.save(countdown);
    }

    @Override
    public boolean updateMyCountdown(Countdown countdown) {
        LambdaQueryWrapper<Countdown> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Countdown::getId, countdown.getId()).eq(Countdown::getUserId, UserContext.getUserId());
        return this.update(countdown, wrapper);
    }

    @Override
    public boolean deleteMyCountdown(Long id) {
        LambdaQueryWrapper<Countdown> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Countdown::getId, id).eq(Countdown::getUserId, UserContext.getUserId());
        return this.remove(wrapper);
    }
}