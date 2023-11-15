package com.proposalmanager.transaction;

import com.proposalmanager.transfer.TransferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "transactions-manager", url = "http://transactions-manager:8000")
public interface FeignServiceTransactionsManager {

    @PostMapping
    TransactionDto makeTransaction(@RequestParam Long userId,
                                   @RequestParam Long thisAccountId,
                                   @RequestParam String descriptionTransaction,
                                   @RequestBody TransferDto transferDto);
}
