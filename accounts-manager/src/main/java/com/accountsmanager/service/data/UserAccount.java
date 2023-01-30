package com.accountsmanager.service.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
    private Long id;
    private Long userId;
    private String accountName;
    private BigDecimal balance;
    private String number;
    private String currency;
    private String currencySymbol;

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", userId=" + userId +
                ", accountName='" + accountName + '\'' +
                ", balance=" + balance +
                ", number='" + number + '\'' +
                ", currency='" + currency + '\'' +
                ", currencySymbol='" + currencySymbol + '\'' +
                '}';
    }
}
