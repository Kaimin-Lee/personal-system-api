package com.personal.system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LedgerDTO {

    @Data
    public static class SaveLedgerDTO {
        private Long id;

        @NotNull(message = "金额不能为空")
        private BigDecimal amount;

        @NotNull(message = "交易类型不能为空")
        private Byte transactionType;

        @NotBlank(message = "分类不能为空")
        private String category;

        private String accountType;

        @NotNull(message = "发生日期不能为空")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate recordDate;

        private String remark;
    }
}
