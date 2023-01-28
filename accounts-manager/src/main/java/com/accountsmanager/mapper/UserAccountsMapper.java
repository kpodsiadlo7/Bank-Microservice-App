package com.accountsmanager.mapper;

import com.accountsmanager.domain.UserAccountEntity;
import com.accountsmanager.service.data.UserAccount;
import com.accountsmanager.web.dto.UserAccountDto;
import org.springframework.stereotype.Service;

@Service
public class UserAccountsMapper {
    public UserAccountDto mapToUserAccountDtoFromUserAccount(final UserAccount userAccount) {
        return new UserAccountDto(
                userAccount.getId(),
                userAccount.getUserId(),
                userAccount.getAccountName(),
                userAccount.getBalance(),
                userAccount.getNumber(),
                userAccount.getCurrency(),
                userAccount.getCurrencySymbol()
        );
    }

    public UserAccount mapToUserAccountFromUserAccountDto(final UserAccountDto userAccountDto) {
        return new UserAccount(
                userAccountDto.getId(),
                userAccountDto.getUserId(),
                userAccountDto.getAccountName(),
                userAccountDto.getBalance(),
                userAccountDto.getNumber(),
                userAccountDto.getCurrency(),
                userAccountDto.getCurrencySymbol()
        );
    }

    public UserAccountEntity mapToUserAccountEntityFromUserAccount(final UserAccount userAccount) {
        return new UserAccountEntity(
                userAccount.getUserId(),
                userAccount.getAccountName(),
                userAccount.getBalance(),
                userAccount.getNumber(),
                userAccount.getCurrency(),
                userAccount.getCurrencySymbol()
        );
    }

    public UserAccount mapToUserAccountFromUserAccountEntity(final UserAccountEntity userAccountEntity) {
        return new UserAccount(
                userAccountEntity.getId(),
                userAccountEntity.getUserId(),
                userAccountEntity.getAccountName(),
                userAccountEntity.getBalance(),
                userAccountEntity.getNumber(),
                userAccountEntity.getCurrency(),
                userAccountEntity.getCurrencySymbol()
        );
    }
}
