package com.mainapp.account;

import com.mainapp.account.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class AccountFacade {
    private final FeignServiceAccountsManager feignServiceAccountsManager;

    public AccountDto getNewEmptyAccount() {
        return AccountDto.builder().build();
    }

    public AccountDto createAccountForUser(Long userId, AccountDto accountDto) {
        return feignServiceAccountsManager.createAccountForUser(userId, accountDto);
    }

    public TreeSet<AccountDto> getAllAccountsByUserId(Long userId) {
        return feignServiceAccountsManager.getAllAccountsByUserId(userId);
    }

    public AccountDto getAccountByAccountId(Long accountId) {
        return feignServiceAccountsManager.getAccountByAccountId(accountId);
    }

    public void setCommitmentsToAccount(Long accountId, double monthlyFee) {
        feignServiceAccountsManager.setCommitmentsToAccount(accountId, monthlyFee);
    }
}
