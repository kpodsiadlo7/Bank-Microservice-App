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
            return createMainAccount(userId,userAccount);
        return createAccountForUser(userId,userAccount);
    }

    private UserAccount createAccountForUser(final Long userId, final UserAccount userAccount) {
        userAccount.setUserId(userId);
        userAccount.setNumber(prepareAccountData(userAccount).getNumber());
        UserAccountEntity accountEntity = userAccountsMapper.mapToUserAccountEntityFromUserAccount(userAccount);
        adapterUserAccountRepository.save(accountEntity);
        return userAccountsMapper.mapToUserAccountFromUserAccountEntity(accountEntity);
    }

    private UserAccount createMainAccount(final Long userId, final UserAccount userAccount) {
        userAccount.setUserId(userId);
        userAccount.setAccountName("Main account");
        userAccount.setCurrency("PLN");
        userAccount.setBalance(new BigDecimal(0));
        userAccount.setNumber(prepareAccountData(userAccount).getNumber());
        userAccount.setCurrencySymbol("zł");
        adapterUserAccountRepository.save(userAccountsMapper.mapToUserAccountEntityFromUserAccount(userAccount));
        return userAccount;
    }

    private UserAccount prepareAccountData(final UserAccount userAccount) {
        String symbol = "";
        userAccount.setBalance(new BigDecimal(0));
        switch (userAccount.getCurrency()) {
            case "PLN" -> {
                symbol = "PL55";
                userAccount.setCurrencySymbol("zł");
            }
            case "EUR" -> {
                symbol = "EU49";
                userAccount.setCurrencySymbol("€");
            }
        }
        Random random = new Random();
        StringBuilder b = new StringBuilder();
        b.append(symbol);
        for (int i = 0; i <= 20; i++) {
            b.append(random.nextInt(7));
        }
        if (adapterUserAccountRepository.existsByNumber(b.toString())) {
            b.setLength(0);
            return prepareAccountData(userAccount);
        }
        userAccount.setNumber(b.toString());
        return userAccount;
    }

    public List<UserAccount> getAllUserAccounts(final Long userId) {
        log.info("get all user accounts start method and user id: " +userId);
        List<UserAccountEntity> accountEntities = adapterUserAccountRepository.findAllByUserId(userId);
        log.info("quantity accounts from db: "+accountEntities.size());
        return userAccountsMapper.mapToUserAccountListFromUserAccountEntityList(accountEntities);
    }
}
