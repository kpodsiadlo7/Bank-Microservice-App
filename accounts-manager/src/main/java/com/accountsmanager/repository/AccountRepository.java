package com.accountsmanager.repository;

import com.accountsmanager.repository.adapter.AdapterAccountRepository;
import com.accountsmanager.domain.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends AdapterAccountRepository, JpaRepository<AccountEntity, Long> {
}
