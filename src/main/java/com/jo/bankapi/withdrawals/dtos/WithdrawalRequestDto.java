package com.jo.bankapi.withdrawals.dtos;


import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class WithdrawalRequestDto {

    @NotNull
    @NotEmpty
    private String bankAccountNo;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=2)
    private BigDecimal amount;
}
