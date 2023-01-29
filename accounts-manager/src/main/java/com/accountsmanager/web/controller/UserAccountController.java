package com.accountsmanager.web.controller;

import com.accountsmanager.service.UserAccountService;
import com.accountsmanager.service.mapper.TransferMapper;
import com.accountsmanager.service.mapper.UserAccountsMapper;
import com.accountsmanager.web.dto.TransferDto;
import com.accountsmanager.web.dto.UserAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/create-account")
    public ResponseEntity<UserAccountDto> createAccount(@RequestParam Long userId,
                                                        @RequestBody UserAccountDto userAccountDto) {
        return ResponseEntity.ok(userAccountsMapper.mapToUserAccountDtoFromUserAccount(userAccountService.validateData
                (userId, userAccountsMapper.mapToUserAccountFromUserAccountDto(userAccountDto))));
    }


    @PostMapping("/quick-transfer")
    public ResponseEntity<TransferDto> quickTransfer(@RequestParam Long userId,
                                                     @RequestBody TransferDto transferDto){
        return ResponseEntity.ok(transferMapper.mapToUserTransferDtoFromUserTransfer
                (userAccountService.validateDataBeforeTransaction(userId,transferMapper.mapToTransferFromTransferDto(transferDto))));
    }
}
