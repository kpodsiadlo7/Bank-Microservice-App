package com.mainapp.web.feign;

import com.mainapp.web.dto.TransferDto;
import com.mainapp.web.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.TreeSet;

@FeignClient(value = "accounts-manager", url = "http://accounts-manager:8010")
public interface FeignServiceAccountsManager {

    @GetMapping("/get-accounts")
    TreeSet<AccountDto> getAllAccountsByUserId(@RequestParam Long userId);
    @GetMapping("/get-account")
    AccountDto getAccountByAccountId(@RequestParam Long accountId);

    @PostMapping("/quick-transfer")
    TransferDto quickTransfer(@RequestParam Long userId,
                              @RequestParam String kindTransaction,
                              @RequestBody TransferDto transferDto);
    @PostMapping("/create-account")
    AccountDto createAccountForUser(@RequestParam Long userId,
                                    @RequestBody AccountDto accountDto);

}
