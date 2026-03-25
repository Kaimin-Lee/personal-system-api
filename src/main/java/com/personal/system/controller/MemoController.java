package com.personal.system.controller;

import com.personal.system.common.BusinessException;
import com.personal.system.common.Result;
import com.personal.system.dto.MemoDTO;
import com.personal.system.entity.Memo;
import com.personal.system.service.IMemoService;
import com.personal.system.utils.UserContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memo")
@RequiredArgsConstructor
public class MemoController {

    private final IMemoService memoService;

    @GetMapping("/list")
    public Result<List<Memo>> list(@RequestParam(required = false) String keyword) {
        return Result.success(memoService.getMyMemos(UserContext.getUserId(), keyword));
    }

    @PostMapping("/save")
    public Result<Memo> save(@Valid @RequestBody MemoDTO.SaveMemoDTO dto) {
        Memo memo = new Memo();
        memo.setId(dto.getId());
        memo.setContent(dto.getContent());
        memo.setBgColor(dto.getBgColor());
        memo.setUserId(UserContext.getUserId());
        if (memo.getId() == null) memo.setIsPinned(false);
        memoService.saveOrUpdate(memo);
        return Result.success("保存成功", memo);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        Memo memo = memoService.getById(id);
        if (memo == null || !memo.getUserId().equals(UserContext.getUserId()))
            throw new BusinessException("备忘录不存在或无权限");
        memoService.removeById(id);
        return Result.success();
    }

    @PostMapping("/pin/{id}")
    public Result<Void> togglePin(@PathVariable Long id) {
        memoService.togglePin(id, UserContext.getUserId());
        return Result.success("置顶状态已更新", null);
    }
}
