package com.accountsmanager.repository;

import com.accountsmanager.repository.adapter.AdapterUserAccountRepository;
import com.accountsmanager.domain.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends AdapterUserAccountRepository, JpaRepository<UserAccountEntity, Long> {
}
