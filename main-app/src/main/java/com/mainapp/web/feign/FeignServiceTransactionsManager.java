package com.mainapp.web.feign;

import com.mainapp.web.dto.TransactionDto;
import com.mainapp.web.dto.TransferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "transactions-manager", url = "http://transactions-manager:8000")
public interface FeignServiceTransactionsManager {

    @PostMapping
    TransactionDto quickTransfer(@RequestParam Long accountToWithdrawId,
                                 @RequestParam String kindTransaction,
                                 @RequestBody TransferDto transferDto);
}
