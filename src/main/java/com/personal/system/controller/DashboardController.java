package com.personal.system.controller;

import com.personal.system.common.Result;
import com.personal.system.service.IDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private IDashboardService dashboardService;

    @GetMapping("/data")
    public Result<Map<String, Object>> getDashboardData(
            @RequestParam(defaultValue = "priority") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder) {

        try {
            Map<String, Object> data = dashboardService.getDashboardData(sortBy, sortOrder);
            return Result.success("获取成功", data);
        } catch (Exception e) {
            return Result.error(500, "系统内部异常: " + e.getMessage());
        }
    }
}