package com.personal.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personal.system.entity.Ledger;
import com.personal.system.mapper.LedgerMapper;
import com.personal.system.service.ILedgerService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LedgerServiceImpl extends ServiceImpl<LedgerMapper, Ledger> implements ILedgerService {

    @Override
    public List<Ledger> getMyLedgers(Long userId, Byte type, String category, String month) {
        LambdaQueryWrapper<Ledger> wrapper = new LambdaQueryWrapper<Ledger>()
                .eq(Ledger::getUserId, userId);
        if (type != null) wrapper.eq(Ledger::getTransactionType, type);
        if (category != null && !category.isEmpty()) wrapper.eq(Ledger::getCategory, category);
        if (month != null && !month.isEmpty()) {
            LocalDate start = LocalDate.parse(month + "-01");
            LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
            wrapper.between(Ledger::getRecordDate, start, end);
        }
        wrapper.orderByDesc(Ledger::getRecordDate).orderByDesc(Ledger::getCreateTime);
        return this.list(wrapper);
    }

    @Override
    public Map<String, Object> getMonthlySummary(Long userId, String month) {
        List<Ledger> list = getMyLedgers(userId, null, null, month);
        BigDecimal income = BigDecimal.ZERO;
        BigDecimal expense = BigDecimal.ZERO;
        for (Ledger l : list) {
            if (l.getTransactionType() == 2) income = income.add(l.getAmount());
            else if (l.getTransactionType() == 1) expense = expense.add(l.getAmount());
        }
        Map<String, Object> summary = new HashMap<>();
        summary.put("income", income);
        summary.put("expense", expense);
        summary.put("balance", income.subtract(expense));
        return summary;
    }
}
