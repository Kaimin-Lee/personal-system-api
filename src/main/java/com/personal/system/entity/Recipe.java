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
 * 个人菜谱库表
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
@Getter
@Setter
@TableName("life_recipe")
@ApiModel(value = "Recipe对象", description = "个人菜谱库表")
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("归属用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("菜品名称")
    @TableField("recipe_name")
    private String recipeName;

    @ApiModelProperty("成品图URL")
    @TableField("cover_url")
    private String coverUrl;

    @ApiModelProperty("所需食材清单 (可用JSON格式或纯文本存储)")
    @TableField("ingredients")
    private String ingredients;

    @ApiModelProperty("制作步骤")
    @TableField("steps")
    private String steps;

    @ApiModelProperty("难度: 1-简单, 2-中等, 3-困难")
    @TableField("difficulty")
    private Byte difficulty;

    @ApiModelProperty("个人评分 (1-5星)")
    @TableField("rating")
    private Byte rating;

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
