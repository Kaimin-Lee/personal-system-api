package com.personal.system.service.impl;

import com.personal.system.entity.Recipe;
import com.personal.system.mapper.RecipeMapper;
import com.personal.system.service.IRecipeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 个人菜谱库表 服务实现类
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
@Service
public class RecipeServiceImpl extends ServiceImpl<RecipeMapper, Recipe> implements IRecipeService {

}
