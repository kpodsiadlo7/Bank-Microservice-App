package com.transactionsmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "transactions_db")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String description;
    private String kindTransaction;
    private LocalDate transactionDate;

    public TransactionEntity(final Long userId, final String description, final String kindTransaction) {
        this.userId = userId;
        this.description = description;
        this.kindTransaction = kindTransaction;
        this.transactionDate = LocalDate.now();
    }
}
