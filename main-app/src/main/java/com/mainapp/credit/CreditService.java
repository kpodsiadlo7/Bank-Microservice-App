package com.mainapp.credit;

import com.mainapp.account.AccountFacade;
import com.mainapp.user.User;
import com.mainapp.account.dto.AccountDto;
import com.mainapp.proposal.ProposalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final AccountFacade accountFacade;

    public String getCredits(final User user, ModelMap modelMap) {
        modelMap.put("proposalDto", new ProposalDto());
        try {
            TreeSet<AccountDto> fetchingAccounts = accountFacade.getAllAccountsByUserId(user.getId());
            modelMap.put("accountsDto", fetchingAccounts);
        } catch (Exception e) {
            modelMap.put("error", "Problem with fetching your accounts");
            modelMap.put("accountsDto", List.of(AccountDto.builder().build()));
            return "credits";
        }
        return "credits";
    }
}
