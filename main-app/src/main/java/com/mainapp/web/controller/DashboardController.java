package com.mainapp.web.controller;

import com.mainapp.service.data.User;
import com.mainapp.service.data.UserAccount;
import com.mainapp.web.dto.UserAccountDto;
import com.mainapp.web.feign.FeignServiceAccountsManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.TreeSet;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final FeignServiceAccountsManager feignServiceAccountsManager;

    @GetMapping
    private String getDashboard(@AuthenticationPrincipal User user, ModelMap modelMap) {
        UserAccount userAccount = new UserAccount();
        modelMap.put("bankAccount", userAccount);
        try {
        modelMap.put("userBankAccounts", feignServiceAccountsManager.getAllUserAccountsByUserId(user.getId()));
        } catch (Exception e){
            modelMap.put("userBankAccounts",new TreeSet<UserAccountDto>());
        }
        return "dashboard";
    }
}
