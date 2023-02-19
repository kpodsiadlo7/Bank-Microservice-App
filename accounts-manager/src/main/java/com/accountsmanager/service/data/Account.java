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
public class Account {
    private Long id;
    private Long userId;
    private String accountName;
    private BigDecimal balance;
    private BigDecimal commitments;
    private String number;
    private String currency;
    private String currencySymbol;

    public void addCommitments(final BigDecimal commitments) {
        this.commitments = this.commitments.add(commitments);
    }
}
