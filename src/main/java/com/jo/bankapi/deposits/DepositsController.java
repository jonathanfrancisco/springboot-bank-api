package com.jo.bankapi.deposits;

import com.jo.bankapi.accounts.AccountsRepository;
import com.jo.bankapi.common.entities.BankAccountEntity;
import com.jo.bankapi.deposits.dtos.BankDepositRequestDto;
import com.jo.bankapi.deposits.dtos.BankDepositResponseDto;
import com.jo.bankapi.deposits.enums.BankDepositType;
import com.jo.bankapi.moneymovement.MoneyMovementService;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/deposits")
public class DepositsController {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private MoneyMovementService moneyMovementService;

    @PostMapping
    public ResponseEntity<Object> deposit(
        @Valid @RequestBody BankDepositRequestDto request
    ) {

        if(!EnumUtils.isValidEnumIgnoreCase(BankDepositType.class, request.getType())) {
            return ResponseEntity.badRequest().body("Invalid bank deposit type. Type should be either CASH or CHECK");
        }

        // Check if bank account exists
        Optional<BankAccountEntity> bankAccountOpt = this.accountsRepository.findByAccountNo(request.getBankAccountNo());
        if(bankAccountOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Bank Account No not found");
        }

        // Credit
        String refNo = moneyMovementService.credit(bankAccountOpt.get(), request.getAmount());

        BankDepositResponseDto resp = BankDepositResponseDto.builder()
                .message("Deposit successful")
                .referenceNo(refNo)
                .details(
                        BankDepositResponseDto.BankDepositDetails.builder()
                                .amount(request.getAmount())
                                .type(request.getType())
                                .build()
                )
                .build();
        return ResponseEntity.ok(resp);

    };
}
