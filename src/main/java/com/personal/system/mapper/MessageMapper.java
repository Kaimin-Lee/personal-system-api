package com.personal.system.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.personal.system.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    // 专门给管理员用的：查询所有跟管理员有过对话的用户ID
    @Select("SELECT DISTINCT CASE WHEN sender_id = 1 THEN receiver_id ELSE sender_id END AS userId " +
            "FROM sys_message WHERE sender_id = 1 OR receiver_id = 1")
    List<Long> getSessionUserIds();
}