package com.transactionsmanager.service.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction{
    private Long id;
    private Long userId;
    private Long accountId;
    private String description;
    private String kindTransaction;
    private LocalDate transactionDate;
    private String value;
}
