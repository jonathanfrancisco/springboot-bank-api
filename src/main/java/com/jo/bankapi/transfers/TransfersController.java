package com.jo.bankapi.transfers;


import com.jo.bankapi.accounts.AccountsRepository;
import com.jo.bankapi.common.entities.BankAccountEntity;
import com.jo.bankapi.deposits.dtos.BankDepositResponseDto;
import com.jo.bankapi.moneymovement.MoneyMovementService;
import com.jo.bankapi.transfers.dtos.TransferRequestDto;
import com.jo.bankapi.transfers.dtos.TransferResponseDto;
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

@RestController
@RequestMapping("/transfers")
public class TransfersController {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private MoneyMovementService moneyMovementService;

    @PostMapping
    public ResponseEntity<Object> transfer(
            @Valid @RequestBody TransferRequestDto request
            ) {

        // Check if source bank exists
        Optional<BankAccountEntity> sourceBankAccountOpt = this.accountsRepository.findByAccountNo(request.getSourceBankAccountNo());
        if(sourceBankAccountOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Source Bank Account No not found");
        }

        // Check source bank balance
        BankAccountEntity sourceBankAccount = sourceBankAccountOpt.get();
        BigDecimal sourceBankCurBal = sourceBankAccount.getBalance();
        boolean isBalSufficient  = sourceBankCurBal.compareTo(request.getAmount()) >= 0;
        if(!isBalSufficient) {
            return ResponseEntity.badRequest().body("Insufficient Balance");
        }

        // Check if dest bank exists
        Optional<BankAccountEntity> destBankAccountOpt = this.accountsRepository.findByAccountNo(request.getDestinationBankAccountNo());
        if(destBankAccountOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Destination Bank Account No not found");
        }
        BankAccountEntity destBankAccount = destBankAccountOpt.get();

        // Debit from source
        String sourceRefNo = moneyMovementService.debit(sourceBankAccount, request.getAmount());

        // Credit to destination
        String destRefNo = moneyMovementService.credit(destBankAccount, request.getAmount());


        TransferResponseDto resp = TransferResponseDto.builder()
                .message("Transfer successful")
                .referenceNo(sourceRefNo)
                .details(
                        TransferResponseDto.TransferDetails.builder()
                                .sourceBankAccountNo(request.getSourceBankAccountNo())
                                .destinationBankAccountNo(request.getDestinationBankAccountNo())
                                .amount(request.getAmount())
                                .build()
                )
                .build();
        return ResponseEntity.ok(resp);
    }
}
