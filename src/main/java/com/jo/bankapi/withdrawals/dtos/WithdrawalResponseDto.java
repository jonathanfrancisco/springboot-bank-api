package com.jo.bankapi.withdrawals.dtos;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;


@Data
@Builder
public class WithdrawalResponseDto {
    private String message;
    private WithdrawalDetails details;
    private String referenceNo;

    @Data
    @Builder
    public static class WithdrawalDetails {
        private BigDecimal amount;
    }
}
