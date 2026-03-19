package com.personal.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personal.system.common.BusinessException;
import com.personal.system.entity.Memo;
import com.personal.system.mapper.MemoMapper;
import com.personal.system.service.IMemoService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MemoServiceImpl extends ServiceImpl<MemoMapper, Memo> implements IMemoService {

    @Override
    public List<Memo> getMyMemos(Long userId, String keyword) {
        LambdaQueryWrapper<Memo> wrapper = new LambdaQueryWrapper<Memo>()
                .eq(Memo::getUserId, userId);
        if (keyword != null && !keyword.isEmpty())
            wrapper.like(Memo::getContent, keyword);
        wrapper.orderByDesc(Memo::getIsPinned).orderByDesc(Memo::getCreateTime);
        return this.list(wrapper);
    }

    @Override
    public void togglePin(Long id, Long userId) {
        Memo memo = this.getOne(new LambdaQueryWrapper<Memo>()
                .eq(Memo::getId, id).eq(Memo::getUserId, userId));
        if (memo == null) throw new BusinessException("备忘录不存在或无权限");
        memo.setIsPinned(!Boolean.TRUE.equals(memo.getIsPinned()));
        this.updateById(memo);
    }
}
