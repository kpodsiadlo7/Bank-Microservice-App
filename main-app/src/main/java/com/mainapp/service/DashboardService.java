package com.mainapp.service;

import com.mainapp.service.data.User;
import com.mainapp.service.mapper.UserAccountsMapper;
import com.mainapp.web.dto.AccountDto;
import com.mainapp.web.dto.TransferDto;
import com.mainapp.web.feign.FeignServiceAccountsManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final FeignServiceAccountsManager feignServiceAccountsManager;
    private final MainService mainService;
    private final UserAccountsMapper userAccountsMapper;

    public boolean makeTransaction(final User user, final Long accountId, final TransferDto transferDto, final String kindTransaction, final ModelMap modelMap) {
        if (!mainService.makeTransaction(user, transferDto, modelMap, kindTransaction, accountId)) {
            try {
                modelMap.put("account", feignServiceAccountsManager.getAccountByAccountId(accountId));
            } catch (Exception e) {
                modelMap.put("error", "There was an error fetching your accounts");
            }
            modelMap.put("quickTransfer", new TransferDto());
            return false;
        }
        return true;
    }

    public String fetchAllAccounts(final User user, final ModelMap modelMap) {
        modelMap.put("quickTransfer", new TransferDto());
        try {
            TreeSet<AccountDto> accounts = feignServiceAccountsManager.getAllUserAccountsByUserId(user.getId());
            if (accounts.size() == 0) {
                try {
                    mainService.createAccountForUser(user.getId(), userAccountsMapper.mapToUserAccountFromUserAccountDto(new AccountDto()));
                    modelMap.put("quickTransfer", new TransferDto());
                    return "redirect:/dashboard";
                } catch (Exception e) {
                    modelMap.put("error", "Failed with loading your accounts");
                }
            }
            modelMap.put("userBankAccounts", accounts);
        } catch (Exception e) {
            modelMap.put("error", "Failed with loading your accounts");
            modelMap.put("userBankAccounts", new TreeSet<AccountDto>());
        }
        return "dashboard";
    }

    public String createAccount(final User user, final AccountDto accountDto, ModelMap modelMap) {
        try {
            mainService.createAccountForUser(user.getId(), userAccountsMapper.mapToUserAccountFromUserAccountDto(accountDto));
        } catch (Exception e) {
            modelMap.put("error", "Failed with creating account");
            modelMap.put("account", new AccountDto());
            return "accounts";
        }
        return "redirect:/dashboard";
    }
}
