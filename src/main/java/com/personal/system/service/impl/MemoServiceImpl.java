package com.personal.system.service.impl;

import com.personal.system.entity.Memo;
import com.personal.system.mapper.MemoMapper;
import com.personal.system.service.IMemoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 生活备忘录表 服务实现类
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
@Service
public class MemoServiceImpl extends ServiceImpl<MemoMapper, Memo> implements IMemoService {

}
