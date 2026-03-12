package com.personal.system.service.impl;

import com.personal.system.entity.HabitLog;
import com.personal.system.mapper.HabitLogMapper;
import com.personal.system.service.IHabitLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 习惯打卡记录表 服务实现类
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
@Service
public class HabitLogServiceImpl extends ServiceImpl<HabitLogMapper, HabitLog> implements IHabitLogService {

}
