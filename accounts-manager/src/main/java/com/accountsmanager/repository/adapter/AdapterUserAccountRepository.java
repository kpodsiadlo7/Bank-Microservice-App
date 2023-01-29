package com.accountsmanager.repository.adapter;

import com.accountsmanager.domain.UserAccountEntity;
import com.accountsmanager.service.UserAccountService;

import java.util.List;

public interface AdapterUserAccountRepository {
    UserAccountEntity save(UserAccountEntity accountEntity);

    List<UserAccountEntity> findAllByUserId(long userId);

    boolean existsByNumber(String number);

    UserAccountEntity findByUserId(long userId);

    UserAccountEntity findByNumber(String number);
}
