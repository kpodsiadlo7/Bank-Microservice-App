package com.accountsmanager.web.controller;

import com.accountsmanager.service.AccountService;
import com.accountsmanager.service.mapper.AccountMapper;
import com.accountsmanager.service.mapper.TransferMapper;
import com.accountsmanager.web.dto.AccountDto;
import com.accountsmanager.web.dto.TransferDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final TransferMapper transferMapper;

    @GetMapping("/get-accounts")
    public ResponseEntity<List<AccountDto>> getAllUserAccountsByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(accountMapper.mapToUserAccountDtoListFromUserAccountList
                (accountService.getAllUserAccounts(userId)));
    }

    @GetMapping("/get-account")
    public ResponseEntity<AccountDto> getAccountByAccountId(@RequestParam Long accountId) {
        log.info("account id: " + accountId);
        return ResponseEntity.ok(accountMapper.mapToUserAccountDtoFromUserAccount(accountService.getAccountByAccountId(accountId)));
    }

    @PostMapping("/create-account")
    public ResponseEntity<AccountDto> createAccount(@RequestParam Long userId,
                                                    @RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(accountMapper.mapToUserAccountDtoFromUserAccount(accountService.validateData
                (userId, accountMapper.mapToUserAccountFromUserAccountDto(accountDto))));
    }

    @PostMapping("/quick-transfer")
    public ResponseEntity<TransferDto> quickTransfer(@RequestParam Long thisAccountId,
                                                     @RequestBody TransferDto transferDto) {
        return ResponseEntity.ok(transferMapper.mapToUserTransferDtoFromUserTransfer
                (accountService.moneyTransferFromUserToUser
                        (thisAccountId, transferMapper.mapToTransferFromTransferDto(transferDto))));
    }

    @PostMapping("/deposit-money")
    public ResponseEntity<TransferDto> depositMoney(@RequestParam Long thisAccountId,
                                                    @RequestBody TransferDto transferDto) {
        return ResponseEntity.ok(transferMapper.mapToUserTransferDtoFromUserTransfer
                (accountService.depositMoney
                        (thisAccountId, transferMapper.mapToTransferFromTransferDto(transferDto))));
    }

    @PostMapping("/withdraw-money")
    public ResponseEntity<TransferDto> withdrawMoney(@RequestParam Long thisAccountId,
                                                     @RequestBody TransferDto transferDto) {
        return ResponseEntity.ok(transferMapper.mapToUserTransferDtoFromUserTransfer
                (accountService.withdrawMoney
                        (thisAccountId, transferMapper.mapToTransferFromTransferDto(transferDto))));
    }

    @PostMapping("/set-commitments")
    public ResponseEntity<Boolean> setCommitmentsToAccountById(@RequestParam Long accountId,
                                                               @RequestParam double monthlyFee) {
        log.info("set commitments controller");
        accountService.setCommitmentsToAccount(accountId, monthlyFee);
        return ResponseEntity.ok().build();
    }
}
