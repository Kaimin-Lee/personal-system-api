package com.personal.system.mapper;

import com.personal.system.entity.Note;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 学习笔记表 Mapper 接口
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
@Mapper
public interface NoteMapper extends BaseMapper<Note> {

}
