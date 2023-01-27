package com.accountsmanager.repository.adapter;

import com.accountsmanager.domain.UserAccountEntity;

public interface AdapterUserAccountRepository {
    UserAccountEntity save(UserAccountEntity accountEntity);
}
