package com.personal.system.mapper;

import com.personal.system.entity.Task;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 待办事项与项目看板表 Mapper 接口
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {

}
