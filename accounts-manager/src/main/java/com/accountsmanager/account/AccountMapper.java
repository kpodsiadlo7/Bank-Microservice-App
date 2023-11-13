package com.accountsmanager.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
class AccountMapper {

    private final AccountFactory accountFactory;

    AccountMapper(AccountFactory accountFactory) {
        this.accountFactory = accountFactory;
    }

    Account mapToUserAccountFromUserAccountDto(final AccountDto accountDto) {
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

    AccountEntity mapToUserAccountEntityFromUserAccount(final Account account) {
        return new AccountEntity(
                account.getUserId(),
                account.getAccountName(),
                account.getBalance(),
                new BigDecimal(0), // no commitments
                account.getNumber(),
                account.getCurrency(),
                account.getCurrencySymbol()
        );
    }

    Account mapToUserAccountFromUserAccountEntity(final AccountEntity accountEntity) {
        return new Account(
                accountEntity.getId(),
                accountEntity.getUserId(),
                accountEntity.getAccountName(),
                accountEntity.getBalance(),
                accountEntity.getCommitments(),
                accountEntity.getNumber(),
                accountEntity.getCurrency(),
                accountEntity.getCurrencySymbol()
        );
    }

    List<Account> mapToUserAccountListFromUserAccountEntityList(final List<AccountEntity> allUserAccounts) {
        List<Account> accounts = new ArrayList<>();
        for (AccountEntity account : allUserAccounts) {
            accounts.add(mapToUserAccountFromUserAccountEntity(account));
        }
        return accounts;
    }

    List<AccountDto> mapToUserAccountDtoListFromUserAccountList(final List<Account> allAccounts) {
        List<AccountDto> accounts = new ArrayList<>();
        for (Account account : allAccounts) {
            accounts.add(accountFactory.buildUserAccountDtoFromUserAccount(account));
        }
        return accounts;
    }

    AccountEntity updateUserAccountEntityFromUserAccount(final Account account) {
        return new AccountEntity(
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
}
