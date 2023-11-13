package com.accountsmanager.account;

import java.util.List;

interface AdapterAccountRepository {
    AccountEntity save(AccountEntity accountEntity);

    List<AccountEntity> findAllByUserId(long userId);

    boolean existsByNumber(String number);

    AccountEntity findByNumber(String number);
    AccountEntity findById(long accountId);
    boolean existsByUserIdAndCurrency(long userId, String currency);
}
