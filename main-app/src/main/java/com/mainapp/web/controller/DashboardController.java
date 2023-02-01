package com.mainapp.web.controller;

import com.mainapp.service.DashboardService;
import com.mainapp.service.MainService;
import com.mainapp.service.data.User;
import com.mainapp.service.mapper.UserAccountsMapper;
import com.mainapp.web.dto.AccountDto;
import com.mainapp.web.dto.TransferDto;
import com.mainapp.web.feign.FeignServiceAccountsManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.TreeSet;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping
    public String getDashboard(@AuthenticationPrincipal User user, ModelMap modelMap) {
        return dashboardService.fetchAllAccounts(user,modelMap);
    }

    @GetMapping("/create-account")
    public String getAccount(ModelMap modelMap) {
        modelMap.put("account", new AccountDto());
        return "accounts";
    }

    @PostMapping("/create-account")
    public String postAccount(@AuthenticationPrincipal User user, @ModelAttribute AccountDto accountDto, ModelMap modelMap) {
        return dashboardService.createAccount(user,accountDto,modelMap);
    }

    @PostMapping
    public String makeTransaction(@AuthenticationPrincipal User user, @RequestParam(name = "accountId") Long accountId, @ModelAttribute TransferDto transferDto,
                                  @RequestParam(name = "descriptionTransaction") String descriptionTransaction, ModelMap modelMap) {
        if (!dashboardService.makeTransaction(user,accountId,transferDto,descriptionTransaction,modelMap))
            return dashboardService.fetchAllAccounts(user,modelMap);

        return "redirect:/dashboard";
    }
}
