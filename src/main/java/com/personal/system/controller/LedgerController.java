package com.personal.system.controller;

import com.personal.system.common.BusinessException;
import com.personal.system.common.Result;
import com.personal.system.dto.LedgerDTO;
import com.personal.system.entity.Ledger;
import com.personal.system.service.ILedgerService;
import com.personal.system.utils.UserContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ledger")
@RequiredArgsConstructor
public class LedgerController {

    private final ILedgerService ledgerService;

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
    public Result<Ledger> save(@Valid @RequestBody LedgerDTO.SaveLedgerDTO dto) {
        Ledger ledger = new Ledger();
        ledger.setId(dto.getId());
        ledger.setAmount(dto.getAmount());
        ledger.setTransactionType(dto.getTransactionType());
        ledger.setCategory(dto.getCategory());
        ledger.setAccountType(dto.getAccountType());
        ledger.setRecordDate(dto.getRecordDate());
        ledger.setRemark(dto.getRemark());
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
