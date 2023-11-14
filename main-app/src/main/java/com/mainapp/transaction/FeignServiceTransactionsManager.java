package com.mainapp.transaction;

import com.mainapp.transfer.TransferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(value = "transactions-manager", url = "http://transactions-manager:8000")
public interface FeignServiceTransactionsManager {

    @GetMapping
    Set<TransactionDto> getAllTransactionByUserId(@RequestParam Long userId);

    @PostMapping
    TransactionDto makeTransaction(@RequestParam Long userId,
                                   @RequestParam Long thisAccountId,
                                   @RequestParam String descriptionTransaction,
                                   @RequestBody TransferDto transferDto);

    @GetMapping("/transactions_by_account_id")
    Set<TransactionDto> getTransactionsByAccountId(@RequestParam Long accountId);
}
