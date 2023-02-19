package com.mainapp.web.controller;

import com.mainapp.service.controller.DashboardService;
import com.mainapp.service.controller.TransactionService;
import com.mainapp.service.data.User;
import com.mainapp.web.dto.TransferDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class TransactionController {
    private final DashboardService dashboardService;
    private final TransactionService transactionService;

    @GetMapping("/transactions")
    public String getAllTransactionsByUserId(@AuthenticationPrincipal User user, ModelMap modelMap) {
        return transactionService.getAllTransactionsByUserId(user.getId(), modelMap);
    }

    @GetMapping("/proposals")
    public String getAllProposalsByUserId(@AuthenticationPrincipal User user, ModelMap modelMap) {
        return transactionService.getAllProposalsByUserId(user.getId(), modelMap);
    }

    @GetMapping("/account/{accountId}")
    public String getAccounts(@PathVariable Long accountId, ModelMap modelMap) {
        return transactionService.getAccounts(accountId, modelMap);
    }


    @PostMapping("/account/{accountId}")
    public String makeTransaction(@AuthenticationPrincipal User user, @PathVariable Long accountId, @ModelAttribute TransferDto transferDto,
                                  @RequestParam(name = "descriptionTransaction") String descriptionTransaction, ModelMap modelMap) {
        if (!dashboardService.makeTransaction(user, accountId, transferDto, descriptionTransaction, modelMap))
            return "account";

        return "redirect:/dashboard/account/" + accountId;
    }
}
