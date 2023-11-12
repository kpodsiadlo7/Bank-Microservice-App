package com.accountsmanager.service.account;

import org.springframework.data.jpa.repository.JpaRepository;

interface AccountRepository extends AdapterAccountRepository, JpaRepository<AccountEntity, Long> {
}
