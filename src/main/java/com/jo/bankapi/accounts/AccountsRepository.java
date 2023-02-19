package com.jo.bankapi.accounts;

import com.jo.bankapi.common.entities.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<BankAccountEntity, Long> {
    public Optional<BankAccountEntity> findByAccountNo(String accountNo);

}
