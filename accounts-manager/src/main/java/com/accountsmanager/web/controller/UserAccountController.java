package com.accountsmanager.web.controller;

import com.accountsmanager.service.UserAccountService;
import com.accountsmanager.service.mapper.TransferMapper;
import com.accountsmanager.service.mapper.UserAccountsMapper;
import com.accountsmanager.web.dto.TransferDto;
import com.accountsmanager.web.dto.UserAccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final UserAccountsMapper userAccountsMapper;
    private final TransferMapper transferMapper;
    @GetMapping("/get-accounts")
    public ResponseEntity<List<UserAccountDto>> getAllUserAccountsByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(userAccountsMapper.mapToUserAccountDtoListFromUserAccountList
                (userAccountService.getAllUserAccounts(userId)));
    }

    @GetMapping("/get-account")
    public ResponseEntity<UserAccountDto> getAccountByAccountId(@RequestParam Long accountId){
        log.info("account id: "+accountId);
        return ResponseEntity.ok(userAccountsMapper.mapToUserAccountDtoFromUserAccount(userAccountService.getAccountByAccountId(accountId)));
    }

    @PostMapping("/create-account")
    public ResponseEntity<UserAccountDto> createAccount(@RequestParam Long userId,
                                                        @RequestBody UserAccountDto userAccountDto) {
        return ResponseEntity.ok(userAccountsMapper.mapToUserAccountDtoFromUserAccount(userAccountService.validateData
                (userId, userAccountsMapper.mapToUserAccountFromUserAccountDto(userAccountDto))));
    }

    @PostMapping("/quick-transfer")
    public ResponseEntity<TransferDto> quickTransfer(@RequestParam Long userDecreaseId,
                                                     @RequestBody TransferDto transferDto){
        return ResponseEntity.ok(transferMapper.mapToUserTransferDtoFromUserTransfer
                (userAccountService.validateDataBeforeTransaction
                        (userDecreaseId,transferMapper.mapToTransferFromTransferDto(transferDto))));
    }
}
