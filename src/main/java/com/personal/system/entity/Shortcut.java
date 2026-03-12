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
 * 快捷导航网址表
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
@Getter
@Setter
@TableName("work_shortcut")
@ApiModel(value = "Shortcut对象", description = "快捷导航网址表")
public class Shortcut implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("归属用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("网站名称")
    @TableField("site_name")
    private String siteName;

    @ApiModelProperty("网站URL")
    @TableField("site_url")
    private String siteUrl;

    @ApiModelProperty("网站图标URL或Base64")
    @TableField("icon_url")
    private String iconUrl;

    @ApiModelProperty("分类(如：开发工具、摸鱼、资讯)")
    @TableField("category")
    private String category;

    @ApiModelProperty("显示排序")
    @TableField("sort_order")
    private Integer sortOrder;

    @TableField("create_time")
    private LocalDateTime createTime;
}
