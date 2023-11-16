package com.mainapp.dashboard;

import com.mainapp.user.User;
import com.mainapp.account.dto.AccountDto;
import com.mainapp.transfer.dto.TransferDto;
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
    private final DashboardFacade dashboardFacade;

    @GetMapping
    String getDashboard(@AuthenticationPrincipal User user, ModelMap modelMap) {
        return dashboardFacade.fetchAllAccounts(user,modelMap);
    }
    @GetMapping("/create-account")
    String getAccount(ModelMap modelMap) {
        modelMap.put("account", AccountDto.builder().build());
        return "accounts";
    }
    @PostMapping("/create-account")
    String postAccount(@AuthenticationPrincipal User user, @ModelAttribute AccountDto accountDto, ModelMap modelMap) {
        return dashboardFacade.createAccount(user,accountDto,modelMap);
    }
    @PostMapping
    String makeTransaction(@AuthenticationPrincipal User user, @RequestParam(name = "accountId") Long accountId, @ModelAttribute TransferDto transferDto,
                                  @RequestParam(name = "descriptionTransaction") String descriptionTransaction, ModelMap modelMap) {
        if (dashboardFacade.makeTransaction(user, accountId, transferDto, descriptionTransaction, modelMap))
            return dashboardFacade.fetchAllAccounts(user,modelMap);

        return "redirect:/dashboard";
    }
}
