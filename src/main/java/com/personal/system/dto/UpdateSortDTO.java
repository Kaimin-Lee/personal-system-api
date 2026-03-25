package com.personal.system.dto;

import lombok.Data;
import java.util.List;

@Data
public class UpdateSortDTO {
    // 目标列的状态 (0-待办, 1-进行中, 2-已完成)
    private Integer status;
    // 排序后的任务 ID 列表
    private List<Long> sortedIds;
}