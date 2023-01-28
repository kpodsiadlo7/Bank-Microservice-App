package com.accountsmanager.service;

import com.accountsmanager.domain.UserAccountEntity;
import com.accountsmanager.repository.adapter.AdapterUserAccountRepository;
import com.accountsmanager.service.data.UserAccount;
import com.accountsmanager.service.mapper.UserAccountsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountsMapper userAccountsMapper;
    private final AdapterUserAccountRepository adapterUserAccountRepository;

    public UserAccount validateData(final Long userId, final UserAccount userAccount) {
        if (userAccount.getCurrency() == null)
            return createMainAccount(userId);
        return createAccountForUser(userId,userAccount);
    }

    private UserAccount createAccountForUser(final Long userId, final UserAccount userAccount) {
        UserAccountEntity accountEntity = userAccountsMapper.mapToUserAccountEntityFromUserAccount(userAccount);
        accountEntity.setUserId(userId);
        accountEntity.setNumber(createAccountNumber(userAccount.getCurrency()));
        return userAccountsMapper.mapToUserAccountFromUserAccountEntity(accountEntity);
    }

    private UserAccount createMainAccount(final Long userId) {
        String accountNumber = createAccountNumber("PLN");
        UserAccount mainAccount = new UserAccount(
                null,
                userId,
                "Main account",
                new BigDecimal(0),
                accountNumber,
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

    public List<UserAccount> getAllUserAccounts(final Long userId) {
        log.info("get all user accounts start method and user id: " +userId);
        List<UserAccountEntity> accountEntities = adapterUserAccountRepository.findAllByUserId(userId);
        log.info("quantity accounts from db: "+accountEntities.size());
        return userAccountsMapper.mapToUserAccountSetFromUserAccountEntitySet(accountEntities);
    }
}
