package com.mainapp.dashboard;

import com.mainapp.MainService;
import com.mainapp.account.Account;
import com.mainapp.user.User;
import com.mainapp.account.AccountFactory;
import com.mainapp.account.AccountDto;
import com.mainapp.transfer.TransferDto;
import com.mainapp.account.FeignServiceAccountsManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final FeignServiceAccountsManager feignServiceAccountsManager;
    private final MainService mainService;
    private final AccountFactory accountFactory;

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
            TreeSet<AccountDto> accounts = feignServiceAccountsManager.getAllAccountsByUserId(user.getId());
            if (accounts.isEmpty()) {
                try {
                    mainService.createAccountForUser(user.getId(), accountFactory.fromAccountDtoToAccount(AccountDto.builder().build()));
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
            Account returningAccount = mainService.createAccountForUser(user.getId(), accountFactory.fromAccountDtoToAccount(accountDto));
            if (returningAccount.getCurrency().equals("exist")){
                modelMap.put("error", "You already have account with that currency");
                modelMap.put("account", AccountDto.builder().build());
                return "accounts";
            }
        } catch (Exception e) {
            modelMap.put("error", "Failed with creating account");
            modelMap.put("account", AccountDto.builder().build());
            return "accounts";
        }
        return "redirect:/dashboard";
    }
}
