package com.jo.bankapi.transfers.dtos;




import com.jo.bankapi.deposits.enums.BankDepositType;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TransferRequestDto {

    @NotEmpty
    @NotNull
    private String sourceBankAccountNo;

    @NotEmpty
    @NotNull
    private String destinationBankAccountNo;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=10, fraction=2)
    private BigDecimal amount;
}
