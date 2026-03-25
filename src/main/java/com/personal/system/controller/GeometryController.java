package com.personal.system.controller;

import com.personal.system.common.Result;
import com.personal.system.dto.GeometryDTO;
import com.personal.system.entity.Geometry;
import com.personal.system.service.IGeometryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/geometry")
@RequiredArgsConstructor
public class GeometryController {

    private final IGeometryService geometryService;

    @GetMapping("/history")
    public Result<List<Geometry>> getHistory() {
        return Result.success(geometryService.getMyHistory());
    }

    @PostMapping("/history")
    public Result<Void> addHistory(@Valid @RequestBody GeometryDTO.AddHistoryDTO dto) {
        Geometry history = new Geometry();
        history.setShapeName(dto.getShapeName());
        history.setParams(dto.getParams());
        history.setResult(dto.getResult());
        geometryService.addHistory(history);
        return Result.success("保存成功", null);
    }

    @DeleteMapping("/history")
    public Result<Void> clearHistory() {
        geometryService.clearMyHistory();
        return Result.success("已清空历史", null);
    }
}
