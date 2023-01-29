package com.accountsmanager.repository.adapter;

import com.accountsmanager.domain.UserAccountEntity;

import java.util.List;

public interface AdapterUserAccountRepository {
    UserAccountEntity save(UserAccountEntity accountEntity);

    List<UserAccountEntity> findAllByUserId(long userId);
    boolean existsByNumber(String number);
}
