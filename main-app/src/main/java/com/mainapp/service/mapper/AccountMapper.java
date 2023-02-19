package com.mainapp.service.mapper;

import com.mainapp.service.data.Account;
import com.mainapp.web.dto.AccountDto;
import org.springframework.stereotype.Service;

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
}
