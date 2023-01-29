package com.accountsmanager.service.mapper;

import com.accountsmanager.domain.UserAccountEntity;
import com.accountsmanager.service.data.UserAccount;
import com.accountsmanager.web.dto.UserAccountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    public List<UserAccount> mapToUserAccountListFromUserAccountEntityList(final List<UserAccountEntity> allUserAccounts) {
        List<UserAccount> accounts = new ArrayList<>();
        for (UserAccountEntity account : allUserAccounts) {
            accounts.add(mapToUserAccountFromUserAccountEntity(account));
        }
        log.info("account after first mapper size: " + accounts.size());
        return accounts;
    }

    public List<UserAccountDto> mapToUserAccountDtoListFromUserAccountList(final List<UserAccount> allUserAccounts) {
        List<UserAccountDto> accounts = new ArrayList<>();
        for (UserAccount account : allUserAccounts) {
            accounts.add(mapToUserAccountDtoFromUserAccount(account));
        }
        log.info("account after second mapper size: " + accounts.size());
        return accounts;
    }
}
