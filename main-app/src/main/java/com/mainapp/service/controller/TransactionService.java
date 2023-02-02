package com.mainapp.service.controller;

import com.mainapp.web.dto.AccountDto;
import com.mainapp.web.dto.TransferDto;
import com.mainapp.web.feign.FeignServiceAccountsManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final FeignServiceAccountsManager feignServiceAccountsManager;


    public String getAccounts(final Long accountId, final ModelMap modelMap) {
        modelMap.put("quickTransfer", new TransferDto());
        try {
            AccountDto accountDto = feignServiceAccountsManager.getAccountByAccountId(accountId);
            modelMap.put("account", accountDto);

        } catch (Exception e) {
            modelMap.put("error", "Failed with loading your account");
            modelMap.put("account", new AccountDto());
        }
        return "account";
    }
}
