package com.transactionsmanager.web.feign;

import com.transactionsmanager.web.dto.TransferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "accounts-manager", url = "http://accounts-manager:8010")
public interface FeignServiceAccountsManager {

    @PostMapping("/quick-transfer")
    TransferDto quickTransfer(@RequestParam Long userId,
                              @RequestBody TransferDto transferDto);
}
