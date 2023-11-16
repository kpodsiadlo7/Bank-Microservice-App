package com.accountsmanager.account;

import com.accountsmanager.account.dto.AccountDto;
import com.accountsmanager.transfer.dto.TransferDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
class AccountController {

    private final AccountFacade accountFacade;

    @GetMapping("/get-accounts")
    ResponseEntity<List<AccountDto>> getAllUserAccountsByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(accountFacade.getAllUserAccountByUserId(userId));
    }

    @GetMapping("/get-account")
    ResponseEntity<AccountDto> getAccountByAccountId(@RequestParam Long accountId) {
        log.info("account id: " + accountId);
        return ResponseEntity.ok(accountFacade.getAccountByAccountId(accountId));
    }

    @PostMapping("/create-account")
    ResponseEntity<AccountDto> createAccount(@RequestParam Long userId,
                                             @RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(accountFacade.createAccount(userId,accountDto));
    }

    @PostMapping("/quick-transfer")
    ResponseEntity<TransferDto> quickTransfer(@RequestParam Long thisAccountId,
                                              @RequestBody TransferDto transferDto) {
        return ResponseEntity.ok(accountFacade.moneyTransferFromUserToUser(thisAccountId,transferDto));
    }

    @PostMapping("/deposit-money")
    ResponseEntity<TransferDto> depositMoney(@RequestParam Long thisAccountId,
                                             @RequestBody TransferDto transferDto) {
        return ResponseEntity.ok(accountFacade.depositMoney(thisAccountId,transferDto));
    }

    @PostMapping("/withdraw-money")
    ResponseEntity<TransferDto> withdrawMoney(@RequestParam Long thisAccountId,
                                              @RequestBody TransferDto transferDto) {
        return ResponseEntity.ok(accountFacade.withdrawMoney(thisAccountId,transferDto));
    }

    @PostMapping("/set-commitments")
    ResponseEntity<Boolean> setCommitmentsToAccountById(@RequestParam Long accountId,
                                                        @RequestParam double monthlyFee) {
        log.info("set commitments controller");
        accountFacade.setCommitmentsToAccount(accountId, monthlyFee);
        return ResponseEntity.ok().build();
    }
}
