package com.accountsmanager.service.account;

import lombok.*;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class AccountDto {
    private final Long id;
    private final Long userId;
    private final BigDecimal balance;
    private final String accountName;
    private final BigDecimal commitments;
    private final String number;
    private final String currency;
    private final String currencySymbol;
    public static BuilderAccountDto builder(){
        return new BuilderAccountDto();
    }
    public AccountDto(final BuilderAccountDto builderAccountDto) {
        id = builderAccountDto.id;
        userId = builderAccountDto.userId;
        balance = builderAccountDto.balance;
        accountName = builderAccountDto.accountName;
        commitments = builderAccountDto.commitments;
        number = builderAccountDto.number;
        currency = builderAccountDto.currency;
        currencySymbol = builderAccountDto.currencySymbol;
    }

    public static class BuilderAccountDto {
        private Long id;
        private Long userId;
        private String accountName;
        private BigDecimal balance;
        private BigDecimal commitments;
        private String number;
        private String currency;
        private String currencySymbol;

        private BuilderAccountDto(){
        }

        public AccountDto build(){
            return new AccountDto(this);
        }

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
