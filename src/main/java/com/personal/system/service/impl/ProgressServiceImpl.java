package com.personal.system.service.impl;

import com.personal.system.entity.Progress;
import com.personal.system.mapper.ProgressMapper;
import com.personal.system.service.IProgressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学习进度追踪表 服务实现类
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
@Service
public class ProgressServiceImpl extends ServiceImpl<ProgressMapper, Progress> implements IProgressService {

}
