package com.accountsmanager.web.controller;

import com.accountsmanager.service.UserAccountService;
import com.accountsmanager.service.mapper.UserAccountsMapper;
import com.accountsmanager.web.dto.UserAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final UserAccountsMapper userAccountsMapper;

    @PostMapping("/createaccount")
    public ResponseEntity<UserAccountDto> createAccount(@RequestParam Long userId,
                                                        @RequestBody UserAccountDto userAccountDto) {
        return ResponseEntity.ok(userAccountsMapper.mapToUserAccountDtoFromUserAccount(userAccountService.createAccountForUser
                (userId, userAccountsMapper.mapToUserAccountFromUserAccountDto(userAccountDto))));
    }
}
