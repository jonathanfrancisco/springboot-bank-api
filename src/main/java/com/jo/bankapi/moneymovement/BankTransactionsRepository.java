package com.jo.bankapi.moneymovement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransactionsRepository extends JpaRepository<BankTransaction, Long> {

}
