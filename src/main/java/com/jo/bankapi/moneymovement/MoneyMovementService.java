package com.jo.bankapi.moneymovement;


import com.jo.bankapi.accounts.AccountsRepository;
import com.jo.bankapi.common.entities.BankAccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class MoneyMovementService {

    @Autowired
    private BankTransactionsRepository bankTransactionRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    @Transactional
    public String debit(BankAccountEntity bankAccount, BigDecimal amount) {
        BigDecimal currentBalance  = bankAccount.getBalance();
        BigDecimal newBalance = currentBalance.subtract(amount);
        bankAccount.setBalance(newBalance);
        accountsRepository.save(bankAccount);

        BankTransaction bankTransaction = BankTransaction.builder()
                .accountNo(bankAccount.getAccountNo())
                .entryType(BankTransaction.BankTransactionType.DEBIT)
                .amount(amount)
                .previousBalance(currentBalance)
                .newBalance(newBalance)
                .build();
        bankTransactionRepository.save(bankTransaction);


        return bankTransaction.getReferenceNo().toString();
    }

    @Transactional
    public String credit(BankAccountEntity bankAccount, BigDecimal amount) {
        BigDecimal currentBalance = bankAccount.getBalance();
        BigDecimal newBalance = currentBalance.add(amount);
        bankAccount.setBalance(newBalance);
        accountsRepository.save(bankAccount);

        BankTransaction bankTransaction = BankTransaction.builder()
                .accountNo(bankAccount.getAccountNo())
                .entryType(BankTransaction.BankTransactionType.CREDIT)
                .amount(amount)
                .previousBalance(currentBalance)
                .newBalance(newBalance)
                .build();
        bankTransactionRepository.save(bankTransaction);

        return bankTransaction.getReferenceNo().toString();
    }
}
