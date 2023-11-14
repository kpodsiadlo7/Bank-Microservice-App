package com.mainapp.account;

import org.springframework.stereotype.Service;

@Service
public class AccountFactory {

    public AccountDto buildAccountDtoFromAccount(final Account account) {
        return new AccountDto(
                account.getId(),
                account.getUserId(),
                account.getAccountName(),
                account.getBalance(),
                account.getCommitments(),
                account.getNumber(),
                account.getCurrency(),
                account.getCurrencySymbol()
        );
    }

    public Account fromAccountDtoToAccount(final AccountDto accountDto) {
        return new Account(
                accountDto.getId(),
                accountDto.getUserId(),
                accountDto.getAccountName(),
                accountDto.getBalance(),
                accountDto.getCommitments(),
                accountDto.getNumber(),
                accountDto.getCurrency(),
                accountDto.getCurrencySymbol()
        );
    }
}
