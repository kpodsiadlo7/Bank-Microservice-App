package com.mainapp.service.mapper;

import com.mainapp.service.data.UserAccount;
import com.mainapp.web.dto.UserAccountDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAccountsMapper {

    public List<UserAccount> mapToUserAccountListFromUserAccountsDtoList(final List<UserAccountDto> accountsDtoList) {
        List<UserAccount> accounts = new ArrayList<>();
        for (UserAccountDto account : accountsDtoList) {
            accounts.add(new UserAccount(
                    account.getId(),
                    account.getAccountName(),
                    account.getBalance(),
                    account.getNumber(),
                    account.getCurrency(),
                    account.getCurrencySymbol()
            ));
        }
        return accounts;
    }


    public UserAccountDto mapToUserAccountDtoFromUserAccount(final UserAccount userAccount) {
        return new UserAccountDto(
                userAccount.getId(),
                userAccount.getAccountName(),
                userAccount.getBalance(),
                userAccount.getNumber(),
                userAccount.getCurrency(),
                userAccount.getCurrencySymbol()
        );
    }
}
