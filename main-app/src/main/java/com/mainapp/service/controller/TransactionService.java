package com.mainapp.service.controller;

import com.mainapp.web.dto.AccountDto;
import com.mainapp.web.dto.ProposalDto;
import com.mainapp.web.dto.TransactionDto;
import com.mainapp.web.dto.TransferDto;
import com.mainapp.web.feign.FeignServiceAccountsManager;
import com.mainapp.web.feign.FeignServiceProposalManager;
import com.mainapp.web.feign.FeignServiceTransactionsManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final FeignServiceAccountsManager feignServiceAccountsManager;
    private final FeignServiceTransactionsManager feignServiceTransactionsManager;
    private final FeignServiceProposalManager feignServiceProposalManager;


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

    public String getAllTransactionsByUserId(final Long userId, ModelMap modelMap) {
        Set<TransactionDto> transactions = new HashSet<>();
        try {
            transactions = feignServiceTransactionsManager.getAllTransactionByUserId(userId);
            TreeSet<TransactionDto> sorted = new TreeSet<>(transactions);
            modelMap.put("transactionsDto", sorted);
        } catch (Exception e) {
            modelMap.put("error", "Problem with fetching your transactions");
            modelMap.put("transactionsDto", transactions);
            return "transactions";
        }
        return "transactions";
    }

    public String getAllProposalsByUserId(final Long userId, ModelMap modelMap) {
        Set<ProposalDto> proposals = new HashSet<>();
        try {
            proposals = feignServiceProposalManager.getAllProposalsByUserId(userId);
            TreeSet<ProposalDto> sorted = new TreeSet<>(proposals);
            modelMap.put("proposalsDto", sorted);
        } catch (Exception e) {
            modelMap.put("error", "Problem with fetching your proposals");
            modelMap.put("proposalsDto", proposals);
            return "proposals";
        }
        return "proposals";
    }
}
