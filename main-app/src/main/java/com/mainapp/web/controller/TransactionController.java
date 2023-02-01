package com.mainapp.web.controller;

import com.mainapp.service.MainService;
import com.mainapp.service.data.User;
import com.mainapp.web.dto.TransferDto;
import com.mainapp.web.dto.AccountDto;
import com.mainapp.web.feign.FeignServiceAccountsManager;
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

    private final MainService mainService;
    private final FeignServiceAccountsManager feignServiceAccountsManager;

    @GetMapping("credit")
    public String credit(){
        return "credit";
    }

    @GetMapping("{accountId}")
    public String quickTransfer(@AuthenticationPrincipal User user, @PathVariable Long accountId, ModelMap modelMap) {
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

    @PostMapping("{accountId}")
    public String makeTransaction(@AuthenticationPrincipal User user, @PathVariable Long accountId, @ModelAttribute TransferDto transferDto,
                                  @RequestParam(name = "descriptionTransaction") String kindTransaction, ModelMap modelMap) {
        log.info("id to withdraw: " + accountId);
        if (!mainService.makeTransaction(user, transferDto, modelMap, kindTransaction, accountId)) {
            try {
                modelMap.put("account", feignServiceAccountsManager.getAccountByAccountId(accountId));
            } catch (Exception e) {
                modelMap.put("error", "There was an error fetching your accounts");
            }
            modelMap.put("quickTransfer", new TransferDto());
            return "account";
        }
        return "redirect:/dashboard/account/"+accountId;
    }
}
