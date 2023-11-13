package com.accountsmanager.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Transfer {
    private Long userReceiveId;
    private Long accountReceiveId;
    private BigDecimal amount;
    private String accountNumber;
}
