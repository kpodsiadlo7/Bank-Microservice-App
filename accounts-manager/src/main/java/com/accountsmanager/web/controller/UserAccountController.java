package com.accountsmanager.web.controller;

import com.accountsmanager.service.UserAccountService;
import com.accountsmanager.service.mapper.UserAccountsMapper;
import com.accountsmanager.web.dto.UserAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.TreeSet;

@RestController
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final UserAccountsMapper userAccountsMapper;

    @PostMapping("/createaccount")
    public ResponseEntity<UserAccountDto> createAccount(@RequestParam Long userId,
                                                        @RequestBody UserAccountDto userAccountDto) {
        return ResponseEntity.ok(userAccountsMapper.mapToUserAccountDtoFromUserAccount(userAccountService.validateData
                (userId, userAccountsMapper.mapToUserAccountFromUserAccountDto(userAccountDto))));
    }

    @GetMapping("/getaccounts")
    public ResponseEntity<List<UserAccountDto>> getAllUserAccountsByUserId(@RequestParam Long userId){
        return ResponseEntity.ok(userAccountsMapper.mapToUserAccountDtoSetFromUserAccountSet
                (userAccountService.getAllUserAccounts(userId)));
    }

    @GetMapping("/warm")
    public void warmUp(){}
}
