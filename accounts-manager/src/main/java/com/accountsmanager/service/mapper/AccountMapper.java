package com.accountsmanager.service.mapper;

import com.accountsmanager.domain.AccountEntity;
import com.accountsmanager.service.data.Account;
import com.accountsmanager.web.dto.AccountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AccountMapper {
    public AccountDto mapToUserAccountDtoFromUserAccount(final Account account) {
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

    public Account mapToUserAccountFromUserAccountDto(final AccountDto accountDto) {
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

    public AccountEntity mapToUserAccountEntityFromUserAccount(final Account account) {
        return new AccountEntity(
                account.getUserId(),
                account.getAccountName(),
                account.getBalance(),
                new BigDecimal(0),
                account.getNumber(),
                account.getCurrency(),
                account.getCurrencySymbol()
        );
    }

    public Account mapToUserAccountFromUserAccountEntity(final AccountEntity accountEntity) {
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

    public List<Account> mapToUserAccountListFromUserAccountEntityList(final List<AccountEntity> allUserAccounts) {
        List<Account> accounts = new ArrayList<>();
        for (AccountEntity account : allUserAccounts) {
            accounts.add(mapToUserAccountFromUserAccountEntity(account));
        }
        return accounts;
    }

    public List<AccountDto> mapToUserAccountDtoListFromUserAccountList(final List<Account> allAccounts) {
        List<AccountDto> accounts = new ArrayList<>();
        for (Account account : allAccounts) {
            accounts.add(mapToUserAccountDtoFromUserAccount(account));
        }
        return accounts;
    }

    public AccountEntity updateUserAccountEntityFromUserAccount(final Account account) {
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
