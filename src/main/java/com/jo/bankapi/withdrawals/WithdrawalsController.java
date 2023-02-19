package com.jo.bankapi.withdrawals;


import com.jo.bankapi.accounts.AccountsRepository;
import com.jo.bankapi.common.entities.BankAccountEntity;
import com.jo.bankapi.moneymovement.MoneyMovementService;
import com.jo.bankapi.withdrawals.dtos.WithdrawalRequestDto;
import com.jo.bankapi.withdrawals.dtos.WithdrawalResponseDto;
import org.apache.coyote.Response;
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
@RequestMapping("/withdrawals")
public class WithdrawalsController {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private MoneyMovementService moneyMovementService;

    @PostMapping
    public ResponseEntity<Object> withdrawal(
            @Valid @RequestBody WithdrawalRequestDto request
    ) {
        // Check if bank account exists
        Optional<BankAccountEntity> bankAccOpt = this.accountsRepository.findByAccountNo(request.getBankAccountNo());
        if(bankAccOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Bank Account No not found");
        }

        BankAccountEntity bankAccount = bankAccOpt.get();
        BigDecimal currentBalance = bankAccount.getBalance();

        boolean isBalSufficient  = currentBalance.compareTo(request.getAmount()) >= 0;
        if(!isBalSufficient) {
            return ResponseEntity.badRequest().body("Insufficient Balance");
        }

        // Debit
        String refNo = moneyMovementService.debit(bankAccount, request.getAmount());

        WithdrawalResponseDto resp = WithdrawalResponseDto.builder()
                .message("Withdrawal successful")
                .referenceNo(refNo)
                .details(
                        WithdrawalResponseDto.WithdrawalDetails.builder()
                                .amount(request.getAmount())
                                .build()
                )
                .build();
        return ResponseEntity.ok(resp);
    }
}
