package com.personal.system.service.impl;

import com.personal.system.entity.Note;
import com.personal.system.mapper.NoteMapper;
import com.personal.system.service.INoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学习笔记表 服务实现类
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements INoteService {

}
