package com.jo.bankapi.common.entities;


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
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="bank_accounts")
public class BankAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String mobileNumber;

    @NotNull
    private String accountNo;

    @DecimalMin(value = "0.0")
    @Digits(integer=10, fraction=2)
    private  BigDecimal balance;


    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE NOT NULL")
    private Instant createdAt;

    @UpdateTimestamp()
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE NULL")
    private Instant updatedAt;

}
