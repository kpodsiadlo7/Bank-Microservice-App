package com.bankwebapp.repository;

import com.bankwebapp.domain.BankAccount;
import com.bankwebapp.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.TreeSet;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    TreeSet<BankAccount> findAllByUserAccount(UserAccount userAccount);
}
