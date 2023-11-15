package com.proposalmanager.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
class Account {
    private final Long id;
    private final Long userId;
    private final String accountName;
    private final BigDecimal balance;
    private final BigDecimal commitments;
    private final String number;
    private final String currency;
    private final String currencySymbol;

    public Account(final BuilderAccount builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.accountName = builder.accountName;
        this.balance = builder.balance;
        this.commitments = builder.commitments;
        this.number = builder.number;
        this.currency = builder.currency;
        this.currencySymbol = builder.currencySymbol;
    }

    public static BuilderAccount builder(){
        return new BuilderAccount();
    }

    public static class BuilderAccount{
        private BuilderAccount(){}
        public Account build(){
            return new Account(this);
        }
        private Long id;
        private Long userId;
        private String accountName;
        private BigDecimal balance;
        private BigDecimal commitments;
        private String number;
        private String currency;
        private String currencySymbol;

        public BuilderAccount withId(Long id) {
            this.id = id;
            return this;
        }

        public BuilderAccount withUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public BuilderAccount withAccountName(String accountName) {
            this.accountName = accountName;
            return this;
        }

        public BuilderAccount withBalance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public BuilderAccount withCommitments(BigDecimal commitments) {
            this.commitments = commitments;
            return this;
        }

        public BuilderAccount withNumber(String number) {
            this.number = number;
            return this;
        }

        public BuilderAccount withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public BuilderAccount withCurrencySymbol(String currencySymbol) {
            this.currencySymbol = currencySymbol;
            return this;
        }
    }

}
