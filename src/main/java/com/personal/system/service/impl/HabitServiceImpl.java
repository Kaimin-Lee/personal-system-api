package com.personal.system.service.impl;

import com.personal.system.entity.Habit;
import com.personal.system.mapper.HabitMapper;
import com.personal.system.service.IHabitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 习惯定义表 服务实现类
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
@Service
public class HabitServiceImpl extends ServiceImpl<HabitMapper, Habit> implements IHabitService {

}
