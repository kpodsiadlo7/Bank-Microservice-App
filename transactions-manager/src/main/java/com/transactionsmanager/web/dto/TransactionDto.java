package com.transactionsmanager.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private Long id;
    private Long userId;
    private String description;
    private String kindTransaction;
    private LocalDate transactionDate;

    @Override
    public String toString() {
        return "TransactionDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", description='" + description + '\'' +
                ", kindTransaction='" + kindTransaction + '\'' +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
