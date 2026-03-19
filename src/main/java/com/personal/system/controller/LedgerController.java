package com.personal.system.controller;

import com.personal.system.common.BusinessException;
import com.personal.system.common.Result;
import com.personal.system.entity.Ledger;
import com.personal.system.service.ILedgerService;
import com.personal.system.utils.UserContext;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ledger")
public class LedgerController {

    private final ILedgerService ledgerService;

    public LedgerController(ILedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @GetMapping("/list")
    public Result<List<Ledger>> list(
            @RequestParam(required = false) Byte type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String month) {
        return Result.success(ledgerService.getMyLedgers(UserContext.getUserId(), type, category, month));
    }

    @GetMapping("/summary")
    public Result<Map<String, Object>> summary(@RequestParam(required = false) String month) {
        return Result.success(ledgerService.getMonthlySummary(UserContext.getUserId(), month));
    }

    @PostMapping("/save")
    public Result<Ledger> save(@RequestBody Ledger ledger) {
        ledger.setUserId(UserContext.getUserId());
        ledgerService.saveOrUpdate(ledger);
        return Result.success("保存成功", ledger);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        Ledger ledger = ledgerService.getById(id);
        if (ledger == null || !ledger.getUserId().equals(UserContext.getUserId()))
            throw new BusinessException("记录不存在或无权限");
        ledgerService.removeById(id);
        return Result.success();
    }
}
