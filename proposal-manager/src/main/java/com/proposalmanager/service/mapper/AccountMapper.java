package com.proposalmanager.service.mapper;

import com.proposalmanager.service.data.Account;
import com.proposalmanager.web.dto.AccountDto;
import org.springframework.stereotype.Service;

@Service
public class AccountMapper {

    public Account mapToUserAccountFromUserAccountDto(final AccountDto accountDto) {
        return new Account(
                accountDto.getId(),
                accountDto.getUserId(),
                accountDto.getAccountName(),
                accountDto.getBalance(),
                accountDto.getCommitments(),
                accountDto.getNumber(),
                accountDto.getCurrency(),
                accountDto.getCurrencySymbol()
        );
    }
}
