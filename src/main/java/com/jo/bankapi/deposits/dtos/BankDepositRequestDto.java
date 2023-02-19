package com.jo.bankapi.deposits.dtos;


import com.jo.bankapi.deposits.enums.BankDepositType;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class BankDepositRequestDto {

    @NotEmpty
    @NotNull
    private String bankAccountNo;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=2)
    private BigDecimal amount;

    @NotNull
    @NotEmpty
    private String type;
}
