package com.accountsmanager.repository;

import com.accountsmanager.repository.adapter.AdapterUserAccountRepository;
import com.accountsmanager.domain.UserAccountEntity;
import com.accountsmanager.service.UserAccountService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.TreeSet;

public interface UserAccountRepository extends AdapterUserAccountRepository, JpaRepository<UserAccountEntity, Long> {
}
