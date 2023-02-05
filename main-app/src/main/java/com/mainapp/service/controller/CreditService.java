package com.mainapp.service.controller;

import com.mainapp.service.data.User;
import com.mainapp.web.dto.AccountDto;
import com.mainapp.web.dto.ProposalDto;
import com.mainapp.web.feign.FeignServiceAccountsManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final FeignServiceAccountsManager feignServiceAccountsManager;

    public String getCredits(final User user, ModelMap modelMap) {
        modelMap.put("proposalDto", new ProposalDto());
        try {
            TreeSet<AccountDto> fetchingAccounts = feignServiceAccountsManager.getAllAccountsByUserId(user.getId());
            modelMap.put("accountsDto", fetchingAccounts);
        } catch (Exception e) {
            modelMap.put("error", "Problem with fetching your accounts");
            modelMap.put("accountsDto", List.of(new AccountDto()));
            return "credits";
        }
        return "credits";
    }
}
