package com.personal.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 习惯定义表
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
@Getter
@Setter
@TableName("life_habit")
@ApiModel(value = "Habit对象", description = "习惯定义表")
public class Habit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("归属用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("习惯名称 (如: 健身、早起、阅读)")
    @TableField("habit_name")
    private String habitName;

    @ApiModelProperty("习惯图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("频率类型: 1-每天, 2-每周定次, 3-每月定次")
    @TableField("frequency_type")
    private Byte frequencyType;

    @ApiModelProperty("目标次数 (配合频率类型使用)")
    @TableField("target_times")
    private Integer targetTimes;

    @ApiModelProperty("状态: 1-进行中, 0-已归档/放弃")
    @TableField("status")
    private Byte status;

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
