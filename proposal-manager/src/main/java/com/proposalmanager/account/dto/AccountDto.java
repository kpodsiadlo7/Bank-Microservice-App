package com.proposalmanager.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
@AllArgsConstructor
public class AccountDto {
    private final Long id;
    private final Long userId;
    private final String accountName;
    private final BigDecimal balance;
    private final BigDecimal commitments;
    private final String number;
    private final String currency;
    private final String currencySymbol;

    public AccountDto(final BuilderAccountDto builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.accountName = builder.accountName;
        this.balance = builder.balance;
        this.commitments = builder.commitments;
        this.number = builder.number;
        this.currency = builder.currency;
        this.currencySymbol = builder.currencySymbol;
    }

    public BuilderAccountDto toBuilder(){
        return new BuilderAccountDto()
                .withId(id)
                .withUserId(userId)
                .withBalance(balance)
                .withAccountName(accountName)
                .withCommitments(commitments)
                .withNumber(number)
                .withCurrency(currency)
                .withCurrencySymbol(currencySymbol);
    }

    public static BuilderAccountDto builder(){
        return new BuilderAccountDto();
    }

    public static class BuilderAccountDto{
        private BuilderAccountDto(){}
        public AccountDto build(){
            return new AccountDto(this);
        }
        private Long id;
        private Long userId;
        private String accountName;
        private BigDecimal balance;
        private BigDecimal commitments;
        private String number;
        private String currency;
        private String currencySymbol;

        public BuilderAccountDto withId(Long id) {
            this.id = id;
            return this;
        }

        public BuilderAccountDto withUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public BuilderAccountDto withAccountName(String accountName) {
            this.accountName = accountName;
            return this;
        }

        public BuilderAccountDto withBalance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public BuilderAccountDto withCommitments(BigDecimal commitments) {
            this.commitments = commitments;
            return this;
        }

        public BuilderAccountDto withNumber(String number) {
            this.number = number;
            return this;
        }

        public BuilderAccountDto withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public BuilderAccountDto withCurrencySymbol(String currencySymbol) {
            this.currencySymbol = currencySymbol;
            return this;
        }
    }
}
