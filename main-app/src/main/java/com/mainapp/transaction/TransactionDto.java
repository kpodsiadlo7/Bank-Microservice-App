package com.mainapp.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
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
