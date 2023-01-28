package com.accountsmanager.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "accounts_db")
public class UserAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String accountName;
    private BigDecimal balance;
    private String number;
    private String currency;
    private String currencySymbol;

    public UserAccountEntity(final Long userId, final String accountName, final BigDecimal balance, final String number, final String currency, final String currencySymbol) {
        this.userId = userId;
        this.accountName = accountName;
        this.balance = balance;
        this.number = number;
        this.currency = currency;
        this.currencySymbol = currencySymbol;
    }
}
