package com.transactionsmanager.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TransactionDto{
    private final Long id;
    private final Long userId;
    private final Long accountId;
    private final String description;
    private final String kindTransaction;
    private final LocalDate transactionDate;
    private final String value;

    public TransactionDto(final BuilderTransactionDto builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.accountId = builder.accountId;
        this.description = builder.description;
        this.kindTransaction = builder.kindTransaction;
        this.transactionDate = builder.transactionDate;
        this.value = builder.value;
    }

    public static BuilderTransactionDto builder(){
        return new BuilderTransactionDto();
    }

    public static class BuilderTransactionDto{
        private BuilderTransactionDto(){}
        public TransactionDto build(){
            return new TransactionDto(this);
        }
        private Long id;
        private Long userId;
        private Long accountId;
        private String description;
        private String kindTransaction;
        private LocalDate transactionDate;
        private String value;

        public BuilderTransactionDto withId(Long id) {
            this.id = id;
            return this;
        }

        public BuilderTransactionDto withUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public BuilderTransactionDto withAccountId(Long accountId) {
            this.accountId = accountId;
            return this;
        }

        public BuilderTransactionDto withDescription(String description) {
            this.description = description;
            return this;
        }

        public BuilderTransactionDto withKindTransaction(String kindTransaction) {
            this.kindTransaction = kindTransaction;
            return this;
        }

        public BuilderTransactionDto withTransactionDate(LocalDate transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        public BuilderTransactionDto withValue(String value) {
            this.value = value;
            return this;
        }
    }
}
