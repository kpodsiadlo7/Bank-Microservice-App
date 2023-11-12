package com.mainapp.web.controller;

import com.mainapp.service.controller.DashboardService;
import com.mainapp.service.data.User;
import com.mainapp.web.dto.AccountDto;
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
class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping
    String getDashboard(@AuthenticationPrincipal User user, ModelMap modelMap) {
        return dashboardService.fetchAllAccounts(user,modelMap);
    }

    @GetMapping("/create-account")
    String getAccount(ModelMap modelMap) {
        modelMap.put("account", new AccountDto());
        return "accounts";
    }

    @PostMapping("/create-account")
    String postAccount(@AuthenticationPrincipal User user, @ModelAttribute AccountDto accountDto, ModelMap modelMap) {
        return dashboardService.createAccount(user,accountDto,modelMap);
    }

    @PostMapping
    String makeTransaction(@AuthenticationPrincipal User user, @RequestParam(name = "accountId") Long accountId, @ModelAttribute TransferDto transferDto,
                                  @RequestParam(name = "descriptionTransaction") String descriptionTransaction, ModelMap modelMap) {
        if (!dashboardService.makeTransaction(user,accountId,transferDto,descriptionTransaction,modelMap))
            return dashboardService.fetchAllAccounts(user,modelMap);

        return "redirect:/dashboard";
    }
}
