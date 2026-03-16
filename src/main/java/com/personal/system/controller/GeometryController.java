package com.personal.system.controller;

import com.personal.system.common.Result;
import com.personal.system.entity.Geometry;
import com.personal.system.service.IGeometryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/geometry")
public class GeometryController {

    @Autowired
    private IGeometryService geometryService;

    @GetMapping("/history")
    public Result<List<Geometry>> getHistory() {
        return Result.success(geometryService.getMyHistory());
    }

    @PostMapping("/history")
    public Result<Void> addHistory(@RequestBody Geometry history) {
        geometryService.addHistory(history);
        return Result.success("保存成功", null);
    }

    @DeleteMapping("/history")
    public Result<Void> clearHistory() {
        geometryService.clearMyHistory();
        return Result.success("已清空历史", null);
    }
}