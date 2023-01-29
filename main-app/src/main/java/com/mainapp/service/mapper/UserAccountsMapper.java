package com.mainapp.service.mapper;

import com.mainapp.service.data.UserAccount;
import com.mainapp.web.dto.UserAccountDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAccountsMapper {


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

    public UserAccount mapToUserAccountFromUserAccountDto(final UserAccountDto accountForUser) {
        return new UserAccount(
                accountForUser.getId(),
                accountForUser.getAccountName(),
                accountForUser.getBalance(),
                accountForUser.getNumber(),
                accountForUser.getCurrency(),
                accountForUser.getCurrencySymbol()
        );
    }
}
