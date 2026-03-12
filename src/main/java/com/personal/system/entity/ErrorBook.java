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
 * 错题本记录表
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
@Getter
@Setter
@TableName("study_error_book")
@ApiModel(value = "ErrorBook对象", description = "错题本记录表")
public class ErrorBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("归属用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("所属科目(如：软件工程、数据结构)")
    @TableField("subject")
    private String subject;

    @ApiModelProperty("题目内容")
    @TableField("question_content")
    private String questionContent;

    @ApiModelProperty("我的错误答案")
    @TableField("wrong_answer")
    private String wrongAnswer;

    @ApiModelProperty("正确答案与解析")
    @TableField("correct_answer")
    private String correctAnswer;

    @ApiModelProperty("错误原因总结(如：粗心、概念混淆)")
    @TableField("reason")
    private String reason;

    @ApiModelProperty("掌握程度: 0-未掌握, 1-半懂, 2-已彻底掌握")
    @TableField("mastery_level")
    private Byte masteryLevel;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
