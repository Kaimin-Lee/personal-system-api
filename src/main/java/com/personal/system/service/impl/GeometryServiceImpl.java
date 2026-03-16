package com.personal.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personal.system.entity.Geometry;
import com.personal.system.mapper.GeometryMapper;
import com.personal.system.service.IGeometryService;
import com.personal.system.utils.UserContext;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GeometryServiceImpl extends ServiceImpl<GeometryMapper, Geometry> implements IGeometryService {

    public List<Geometry> getMyHistory() {
        return this.list(new LambdaQueryWrapper<Geometry>()
                .eq(Geometry::getUserId, UserContext.getUserId())
                .orderByDesc(Geometry::getCreateTime)
                .last("LIMIT 50")); // 最多展示近50条
    }

    public void addHistory(Geometry history) {
        history.setUserId(UserContext.getUserId());
        history.setCreateTime(LocalDateTime.now());
        this.save(history);
    }

    public void clearMyHistory() {
        this.remove(new LambdaQueryWrapper<Geometry>()
                .eq(Geometry::getUserId, UserContext.getUserId()));
    }
}