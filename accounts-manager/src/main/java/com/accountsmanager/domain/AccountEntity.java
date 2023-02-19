package com.accountsmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts_db")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String accountName;
    private BigDecimal balance;
    private BigDecimal commitments;
    private String number;
    private String currency;
    private String currencySymbol;

    public AccountEntity(final Long userId, final String accountName, final BigDecimal balance, final BigDecimal commitments, final String number, final String currency, final String currencySymbol) {
        this.userId = userId;
        this.accountName = accountName;
        this.balance = balance;
        this.commitments = commitments;
        this.number = number;
        this.currency = currency;
        this.currencySymbol = currencySymbol;
    }
}
