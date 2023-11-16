package com.mainapp.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto implements Comparable<TransactionDto>{
    private Long id;
    private Long userId;
    private String description;
    private String kindTransaction;
    private LocalDate transactionDate;
    private String value;

    @Override
    public int compareTo(final TransactionDto o) {
        return this.id.compareTo(o.getId());
    }
}
