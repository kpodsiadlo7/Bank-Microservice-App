package com.proposalmanager.transaction;

import com.proposalmanager.transaction.dto.TransactionDto;
import com.proposalmanager.transfer.dto.TransferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "transactions-manager", url = "http://transactions-manager:8000")
interface FeignServiceTransactionsManager {

    @PostMapping
    TransactionDto makeTransaction(@RequestParam Long userId,
                                   @RequestParam Long thisAccountId,
                                   @RequestParam String descriptionTransaction,
                                   @RequestBody TransferDto transferDto);
}
