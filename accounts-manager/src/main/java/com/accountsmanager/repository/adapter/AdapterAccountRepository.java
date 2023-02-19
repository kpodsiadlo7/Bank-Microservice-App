package com.accountsmanager.repository.adapter;

import com.accountsmanager.domain.AccountEntity;

import java.util.List;

public interface AdapterAccountRepository {
    AccountEntity save(AccountEntity accountEntity);

    List<AccountEntity> findAllByUserId(long userId);

    boolean existsByNumber(String number);

    AccountEntity findByUserId(long userId);

    AccountEntity findByNumber(String number);
    AccountEntity findById(long accountId);
    boolean existsByUserIdAndCurrency(long userId, String currency);
}
