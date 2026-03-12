package com.personal.system.mapper;

import com.personal.system.entity.Recipe;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 个人菜谱库表 Mapper 接口
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
@Mapper
public interface RecipeMapper extends BaseMapper<Recipe> {

}
