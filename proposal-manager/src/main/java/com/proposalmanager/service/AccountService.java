package com.proposalmanager.service;

import com.proposalmanager.service.data.Account;
import com.proposalmanager.service.mapper.AccountMapper;
import com.proposalmanager.web.feign.FeignServiceAccountsManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final FeignServiceAccountsManager feignServiceAccountsManager;
    private final AccountMapper accountMapper;
    Account fetchAccountByAccountId(final Long accountId, final Account error) {
        log.info("fetch account by account id");
        try {
            return accountMapper.mapToUserAccountFromUserAccountDto
                    (feignServiceAccountsManager.getAccountByAccountId(accountId));
        } catch (Exception e) {
            log.warn("problem with connecting to account manager");
            // this user id is completely not needed for anything, I set it only for validation
            error.setUserId(-1L);
            error.setAccountName("There is a problem with connecting to account manager");
            return error;
        }
    }
}
