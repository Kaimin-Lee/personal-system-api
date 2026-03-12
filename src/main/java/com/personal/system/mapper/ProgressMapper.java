package com.personal.system.mapper;

import com.personal.system.entity.Progress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 学习进度追踪表 Mapper 接口
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
@Mapper
public interface ProgressMapper extends BaseMapper<Progress> {

}
