package com.mainapp.web.feign;

import com.mainapp.web.dto.UserAccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "accounts-manager", url = "http://accounts-manager:8010")
public interface FeignServiceAccountsManager {
    @PostMapping("/createaccount")
    UserAccountDto createAccountForUser(@RequestParam Long userId,
                                        @RequestBody UserAccountDto userAccountDto);
}
