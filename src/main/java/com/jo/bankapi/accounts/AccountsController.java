package com.jo.bankapi.accounts;

import com.jo.bankapi.accounts.dtos.OpenBankAccountRequestDto;
import com.jo.bankapi.accounts.dtos.OpenBankAccountResponseDto;
import com.jo.bankapi.common.entities.BankAccountEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;





@RestController
@RequestMapping("/accounts")
public class AccountsController {

    @Autowired
    private AccountsRepository accountsRepository;

    Logger logger = LoggerFactory.getLogger(AccountsController.class);

    @PostMapping("/open-bank-account")
    public OpenBankAccountResponseDto openBankAccount(
            @Valid @RequestBody OpenBankAccountRequestDto body
            ) throws Exception {
            logger.info("Request received. Going to open a bank account");

            logger.info(ZonedDateTime.now().toString());
            logger.info(Instant.now().toString());
            logger.info(LocalDateTime.now().toString());

            String newId = UUID.randomUUID().toString();
            String bankAccountNo = UUID.randomUUID().toString(); // TODO: Replace with real bank account no e.g 12-16 digit

            BankAccountEntity newBankAcc = BankAccountEntity.builder()
                    .firstName(body.getFirstName())
                    .lastName(body.getLastName())
                    .mobileNumber(body.getMobileNumber())
                    .accountNo(bankAccountNo)
                    .balance(BigDecimal.valueOf(0.00))
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
                    .build();
            this.accountsRepository.save(newBankAcc);

            logger.info("Bank account successfully created");

            return OpenBankAccountResponseDto
                    .builder()
                    .message("You have successfully opened a bank account!")
                    .bankAccountNo(bankAccountNo)
                    .build();
    }

    @GetMapping
    public List<BankAccountEntity> getBankAccounts() {
        List<BankAccountEntity> bankAccs = this.accountsRepository.findAll();

        return bankAccs;
    }
}
