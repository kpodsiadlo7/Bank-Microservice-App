package com.proposalmanager.web.feign;

import com.proposalmanager.web.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "accounts-manager", url = "http://accounts-manager:8010")
public interface FeignServiceAccountsManager {

    @GetMapping("/get-account")
    AccountDto getAccountByAccountId(@RequestParam Long accountId);

}
