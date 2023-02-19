package com.jo.bankapi.deposits.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Builder
public class BankDepositResponseDto {
    private String message;
    private BankDepositDetails details;
    private String referenceNo;

    @Data
    @Builder
    public static class BankDepositDetails {
        private BigDecimal amount;
        private String type;
    }
}
