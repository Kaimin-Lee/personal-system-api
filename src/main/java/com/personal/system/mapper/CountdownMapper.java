package com.personal.system.mapper;

import com.personal.system.entity.Countdown;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 重要事件倒数日表 Mapper 接口
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
@Mapper
public interface CountdownMapper extends BaseMapper<Countdown> {

}
