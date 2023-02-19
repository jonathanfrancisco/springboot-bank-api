package com.jo.bankapi.transfers.dtos;



import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Builder
public class TransferResponseDto {
    private String message;
    private TransferDetails details;
    private String referenceNo;

    @Data
    @Builder
    public static class TransferDetails {
        private String sourceBankAccountNo;
        private String destinationBankAccountNo;
        private BigDecimal amount;

    }
}
