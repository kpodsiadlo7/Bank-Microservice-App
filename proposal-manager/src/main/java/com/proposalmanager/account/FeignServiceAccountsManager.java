package com.proposalmanager.account;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "accounts-manager", url = "http://accounts-manager:8010")
interface FeignServiceAccountsManager {

    @GetMapping("/get-account")
    AccountDto getAccountByAccountId(@RequestParam Long accountId);

}
