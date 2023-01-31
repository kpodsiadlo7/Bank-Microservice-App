package com.transactionsmanager.service.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    private Long userReceiveId;
    private Long accountReceiveId;
    private BigDecimal amount;
    private String userAccountNumber;
}
