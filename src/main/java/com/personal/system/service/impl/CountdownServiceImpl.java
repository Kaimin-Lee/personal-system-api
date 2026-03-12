package com.personal.system.service.impl;

import com.personal.system.entity.Countdown;
import com.personal.system.mapper.CountdownMapper;
import com.personal.system.service.ICountdownService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 重要事件倒数日表 服务实现类
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
@Service
public class CountdownServiceImpl extends ServiceImpl<CountdownMapper, Countdown> implements ICountdownService {

}
