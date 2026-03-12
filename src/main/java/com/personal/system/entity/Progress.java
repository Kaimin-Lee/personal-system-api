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
 * 学习进度追踪表
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
@Getter
@Setter
@TableName("study_progress")
@ApiModel(value = "Progress对象", description = "学习进度追踪表")
public class Progress implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("归属用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("学习目标名称")
    @TableField("target_name")
    private String targetName;

    @ApiModelProperty("总任务量(如：总章节数、总页数)")
    @TableField("total_units")
    private Integer totalUnits;

    @ApiModelProperty("已完成任务量")
    @TableField("completed_units")
    private Integer completedUnits;

    @ApiModelProperty("单位名称(章/节/页)")
    @TableField("unit_name")
    private String unitName;

    @ApiModelProperty("状态: 0-进行中, 1-已完成")
    @TableField("status")
    private Byte status;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
