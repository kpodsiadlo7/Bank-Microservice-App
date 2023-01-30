package com.mainapp.web.controller;

import com.mainapp.service.MainService;
import com.mainapp.service.data.User;
import com.mainapp.web.dto.TransferDto;
import com.mainapp.web.dto.UserAccountDto;
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
@RequestMapping("/dashboard/account/")
public class TransactionController {

    private final MainService mainService;
    private final FeignServiceAccountsManager feignServiceAccountsManager;

    @GetMapping("{accountId}")
    public String test(@AuthenticationPrincipal User user, @PathVariable Long accountId, ModelMap modelMap) {
        modelMap.put("quickTransfer", new TransferDto());
        try {
            UserAccountDto userAccountDto = feignServiceAccountsManager.getAccountByAccountId(accountId);
            modelMap.put("userAccount", userAccountDto);

        } catch (Exception e) {
            modelMap.put("error", "Failed with loading your account");
            modelMap.put("userAccount", new UserAccountDto());
        }
        return "account";
    }

    @PostMapping("{accountId}")
    public String makeTransaction(@AuthenticationPrincipal User user, @PathVariable Long accountId, @ModelAttribute TransferDto transferDto,
                          @RequestParam(name = "kindTransaction") String kindTransaction, ModelMap modelMap){
        if (!mainService.quickTransferMoney(user, transferDto, modelMap, kindTransaction,accountId)) {
            try {
                modelMap.put("userAccount", feignServiceAccountsManager.getAccountByAccountId(accountId));
            } catch (Exception e) {
                modelMap.put("error", "There was an error fetching your accounts");
            }
            modelMap.put("quickTransfer", new TransferDto());
            return "account";
        }
        return "redirect:/account/"+accountId;
    }
}
