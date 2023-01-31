package com.mainapp.service.mapper;

import com.mainapp.service.data.Account;
import com.mainapp.web.dto.AccountDto;
import org.springframework.stereotype.Service;

@Service
public class UserAccountsMapper {


    public AccountDto mapToUserAccountDtoFromUserAccount(final Account account) {
        return new AccountDto(
                account.getId(),
                account.getAccountName(),
                account.getBalance(),
                account.getNumber(),
                account.getCurrency(),
                account.getCurrencySymbol()
        );
    }

    public Account mapToUserAccountFromUserAccountDto(final AccountDto accountForUser) {
        return new Account(
                accountForUser.getId(),
                accountForUser.getAccountName(),
                accountForUser.getBalance(),
                accountForUser.getNumber(),
                accountForUser.getCurrency(),
                accountForUser.getCurrencySymbol()
        );
    }
}
