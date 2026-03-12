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
 * 待办事项与项目看板表
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
@Getter
@Setter
@TableName("work_task")
@ApiModel(value = "Task对象", description = "待办事项与项目看板表")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("归属用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("任务名称")
    @TableField("title")
    private String title;

    @ApiModelProperty("任务详细描述")
    @TableField("description")
    private String description;

    @ApiModelProperty("状态: 0-待办(To Do), 1-进行中(Doing), 2-已完成(Done)")
    @TableField("status")
    private Byte status;

    @ApiModelProperty("优先级: 0-低, 1-中, 2-高")
    @TableField("priority")
    private Byte priority;

    @ApiModelProperty("看板拖拽排序序号")
    @TableField("sort_order")
    private Integer sortOrder;

    @ApiModelProperty("截止日期")
    @TableField("deadline")
    private LocalDateTime deadline;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
