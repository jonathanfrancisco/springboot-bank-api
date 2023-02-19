package com.jo.bankapi.accounts.dtos;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpenBankAccountResponseDto {
    private String message;
    private String bankAccountNo;
}
