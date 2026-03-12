package com.personal.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 学习笔记表
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
@Getter
@Setter
@TableName("study_note")
@ApiModel(value = "Note对象", description = "学习笔记表")
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("归属用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("笔记标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("笔记正文(存储Markdown或HTML)")
    @TableField("content")
    private String content;

    @ApiModelProperty("所属文件夹ID(预留支持树形目录)")
    @TableField("folder_id")
    private Long folderId;

    @ApiModelProperty("标签，逗号分割")
    @TableField("tags")
    private String tags;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
