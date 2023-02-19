package com.jo.bankapi.moneymovement;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="bank_transactions")
public class BankTransaction {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private String accountNo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private BankTransactionType entryType;
    @DecimalMin(value = "0.0")
    @Digits(integer=10, fraction=2)
    private  BigDecimal amount;

    @DecimalMin(value = "0.0")
    @Digits(integer=10, fraction=2)
    private  BigDecimal previousBalance;


    @DecimalMin(value = "0.0")
    @Digits(integer=10, fraction=2)
    private  BigDecimal  newBalance;

    @NotNull
    private UUID referenceNo;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE NOT NULL")
    private Instant createdAt;

    @UpdateTimestamp()
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE NULL")
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        this.referenceNo = java.util.UUID.randomUUID();
    }

    public static enum BankTransactionType {
        DEBIT,
        CREDIT
    }

}
