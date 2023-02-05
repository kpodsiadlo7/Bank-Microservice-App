package com.transactionsmanager.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto{
    private Long id;
    private Long userId;
    private Long accountId;
    private String description;
    private String kindTransaction;
    private LocalDate transactionDate;
    private String value;
}
