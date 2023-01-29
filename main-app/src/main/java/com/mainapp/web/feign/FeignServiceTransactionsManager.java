package com.mainapp.web.feign;

import com.mainapp.web.dto.TransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "transactions-manager", url = "http://transactions-manager:8000")
public interface FeignServiceTransactionsManager {

    @PostMapping("/transaction")
    TransactionDto postTransaction(@RequestParam String kindTransaction,
                                   @RequestParam Long userId);

}
