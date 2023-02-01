package com.mainapp.web.controller;

import com.mainapp.service.DashboardService;
import com.mainapp.service.TransactionService;
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
@RequestMapping("/dashboard/account")
public class TransactionController {
    private final DashboardService dashboardService;
    private final TransactionService transactionService;

    @GetMapping("credit")
    public String credit() {
        return "credit";
    }

    @GetMapping("{accountId}")
    public String quickTransfer(@AuthenticationPrincipal User user, @PathVariable Long accountId, ModelMap modelMap) {
        return transactionService.quickTransfer(accountId, modelMap);
    }

    @PostMapping("{accountId}")
    public String makeTransaction(@AuthenticationPrincipal User user, @PathVariable Long accountId, @ModelAttribute TransferDto transferDto,
                                  @RequestParam(name = "descriptionTransaction") String descriptionTransaction, ModelMap modelMap) {
        if (!dashboardService.makeTransaction(user, accountId, transferDto, descriptionTransaction, modelMap))
            return dashboardService.fetchAllAccounts(user, modelMap);

        return "redirect:/dashboard/account/" + accountId;
    }
}
