package com.personal.system.mapper;

import com.personal.system.entity.Memo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 生活备忘录表 Mapper 接口
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
@Mapper
public interface MemoMapper extends BaseMapper<Memo> {

}
