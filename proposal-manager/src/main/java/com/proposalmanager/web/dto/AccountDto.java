package com.proposalmanager.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private Long userId;
    private String accountName;
    private BigDecimal balance;
    private BigDecimal commitments;
    private String number;
    private String currency;
    private String currencySymbol;
}
