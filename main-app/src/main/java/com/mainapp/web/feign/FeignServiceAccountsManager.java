package com.mainapp.web.feign;

import com.mainapp.web.dto.UserAccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.TreeSet;

@FeignClient(value = "accounts-manager", url = "http://accounts-manager:8010")
public interface FeignServiceAccountsManager {
    @PostMapping("/create-account")
    UserAccountDto createAccountForUser(@RequestParam Long userId,
                                        @RequestBody UserAccountDto userAccountDto);

    @GetMapping("/get-accounts")
    TreeSet<UserAccountDto> getAllUserAccountsByUserId(@RequestParam Long userId);
}
