package com.personal.system.mapper;

import com.personal.system.entity.Habit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 习惯定义表 Mapper 接口
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
@Mapper
public interface HabitMapper extends BaseMapper<Habit> {

}
