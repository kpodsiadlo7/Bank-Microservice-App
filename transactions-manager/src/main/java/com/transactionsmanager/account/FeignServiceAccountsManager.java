package com.transactionsmanager.account;

import com.transactionsmanager.transfer.dto.TransferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "accounts-manager", url = "http://accounts-manager:8010")
interface FeignServiceAccountsManager {

    @PostMapping("/quick-transfer")
    TransferDto quickTransfer(@RequestParam Long thisAccountId,
                              @RequestBody TransferDto transferDto);

    @PostMapping("/deposit-money")
    TransferDto depositMoney(@RequestParam Long thisAccountId,
                             @RequestBody TransferDto transferDto);
    @PostMapping("/withdraw-money")
    TransferDto withdrawMoney(@RequestParam Long thisAccountId,
                             @RequestBody TransferDto transferDto);
}
