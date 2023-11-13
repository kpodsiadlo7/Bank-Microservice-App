package com.accountsmanager.account;

import com.accountsmanager.account.dto.AccountDto;
import org.springframework.stereotype.Service;

@Service
class AccountFactory {
    AccountDto buildUserAccountDtoFromUserAccount(final Account account) {
        return AccountDto.builder()
                .withId(account.getId())
                .withUserId(account.getUserId())
                .withAccountName(account.getAccountName())
                .withBalance(account.getBalance())
                .withCommitments(account.getCommitments())
                .withNumber(account.getNumber())
                .withCurrency(account.getCurrency())
                .withCurrencySymbol(account.getCurrencySymbol()).build();
    }
}
