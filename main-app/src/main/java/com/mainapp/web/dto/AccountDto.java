package com.mainapp.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto implements Comparable<AccountDto>{
    private Long id;
    private String accountName;
    private BigDecimal balance;
    private String number;
    private String currency;
    private String currencySymbol;

    @Override
    public String toString() {
        return ", currency='" + currency + '\'';
    }

    @Override
    public int compareTo(final AccountDto o) {
        return this.id.compareTo(o.getId());
    }
}
