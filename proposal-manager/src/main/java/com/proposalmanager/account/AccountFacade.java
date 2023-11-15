package com.proposalmanager.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountFacade {

    private final FeignServiceAccountsManager feignServiceAccountsManager;
    public AccountDto fetchAccountByAccountId(final Long accountId, final AccountDto error) {
        log.info("fetch account by account id");
        try {
            return feignServiceAccountsManager.getAccountByAccountId(accountId);
        } catch (Exception e) {
            log.warn("problem with connecting to account manager");
            // this user id is completely not needed for anything, I set it only for validation
            error.toBuilder().withUserId(-1L).withAccountName("There is a problem with connecting to account manager").build();
            return error;
        }
    }

    public AccountDto getAccountByAccountId(final Long accountReceiveId) {
        return feignServiceAccountsManager.getAccountByAccountId(accountReceiveId);
    }
}
