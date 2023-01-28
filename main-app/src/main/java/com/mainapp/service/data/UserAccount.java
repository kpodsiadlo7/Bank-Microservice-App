package com.mainapp.service.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount implements Comparable<UserAccount>{
    private Long id;
    private String accountName;
    private BigDecimal balance;
    private String number;
    private String currency;
    private String currencySymbol;

    @Override
    public int compareTo(final UserAccount o) {
        return this.id.compareTo(o.getId());
    }
}
