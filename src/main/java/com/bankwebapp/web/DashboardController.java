package com.bankwebapp.web;

import com.bankwebapp.domain.BankAccount;
import com.bankwebapp.domain.User;
import com.bankwebapp.domain.UserAccount;
import com.bankwebapp.service.DashboardService;
import com.bankwebapp.service.UserAccountService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    DashboardController(final DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    private String getDashboard(@AuthenticationPrincipal User user, ModelMap modelMap) {
        //BankAccount bankAccount = new BankAccount();
        modelMap.put("userBankAccounts",dashboardService.getAllUserAccountsByUserId(user));
        //modelMap.put("bankAccount",bankAccount);
        return "dashboard";
    }
}
