package com.accountsmanager.service;

import com.accountsmanager.domain.UserAccountEntity;
import com.accountsmanager.repository.adapter.AdapterUserAccountRepository;
import com.accountsmanager.service.data.UserAccount;
import com.accountsmanager.service.mapper.UserAccountsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountsMapper userAccountsMapper;
    private final AdapterUserAccountRepository adapterUserAccountRepository;

    public UserAccount createAccountForUser(final Long userId, final UserAccount userAccount) {
        if (userAccount.getAccountName().equals("Main account"))
            return createMainAccount(userId);
        UserAccountEntity accountEntity = userAccountsMapper.mapToUserAccountEntityFromUserAccount(userAccount);
        accountEntity.setUserId(userId);
        accountEntity.setNumber(createAccountNumber(userAccount.getCurrency()));
        log.info("account is created: OK");
        return new UserAccount();
    }

    private UserAccount createMainAccount(final Long userId) {
        UserAccount mainAccount = new UserAccount(
                null,
                userId,
                "Main account",
                new BigDecimal(0),
                createAccountNumber("PLN"),
                "PLN",
                "zł"
        );
        adapterUserAccountRepository.save(userAccountsMapper.mapToUserAccountEntityFromUserAccount(mainAccount));
        return mainAccount;
    }

    private String createAccountNumber(final String bankNumberSymbol) {
        String symbol = switch (bankNumberSymbol) {
            case "PLN" -> "PL";
            case "EUR" -> "DE";
            default -> "";
        };
        Random random = new Random();
        StringBuilder b = new StringBuilder();
        b.append(symbol);
        for (int i = 0; i <= 20; i++) {
            b.append(random.nextInt(7));
        }
        return b.toString();
    }
}
