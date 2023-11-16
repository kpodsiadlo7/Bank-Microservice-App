package com.transactionsmanager.transaction;

import lombok.Getter;

import java.time.LocalDate;

@Getter
class Transaction{
    private final Long id;
    private final Long userId;
    private final Long accountId;
    private final String description;
    private final String kindTransaction;
    private final LocalDate transactionDate;
    private final String value;

    private Transaction(Long id, Long userId, Long accountId, String description, String kindTransaction, LocalDate transactionDate, String value) {
        this.id = id;
        this.userId = userId;
        this.accountId = accountId;
        this.description = description;
        this.kindTransaction = kindTransaction;
        this.transactionDate = transactionDate;
        this.value = value;
    }

    public Transaction(final BuilderTransaction builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.accountId = builder.accountId;
        this.description = builder.description;
        this.kindTransaction = builder.kindTransaction;
        this.transactionDate = builder.transactionDate;
        this.value = builder.value;
    }

    public static BuilderTransaction builder(){
        return new BuilderTransaction();
    }

    public static class BuilderTransaction{
        private BuilderTransaction(){}
        public Transaction build(){
            return new Transaction(this);
        }
        private Long id;
        private Long userId;
        private Long accountId;
        private String description;
        private String kindTransaction;
        private LocalDate transactionDate;
        private String value;

        public BuilderTransaction withId(Long id) {
            this.id = id;
            return this;
        }

        public BuilderTransaction withUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public BuilderTransaction withAccountId(Long accountId) {
            this.accountId = accountId;
            return this;
        }

        public BuilderTransaction withDescription(String description) {
            this.description = description;
            return this;
        }

        public BuilderTransaction withKindTransaction(String kindTransaction) {
            this.kindTransaction = kindTransaction;
            return this;
        }

        public BuilderTransaction withTransactionDate(LocalDate transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        public BuilderTransaction withValue(String value) {
            this.value = value;
            return this;
        }
    }
}
