package com.personal.system.service;

import com.personal.system.entity.Ledger;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 记账本记录表 服务类
 * </p>
 *
 * @author YueLin
 * @since 2026-03-10
 */
public interface ILedgerService extends IService<Ledger> {

    List<Ledger> getMyLedgers(Long userId, Byte type, String category, String month);

    Map<String, Object> getMonthlySummary(Long userId, String month);
}
