package com.personal.system.service;

import java.util.Map;

public interface IDashboardService {
    // 根据排序条件获取看板聚合数据
    Map<String, Object> getDashboardData(String sortBy, String sortOrder);
}