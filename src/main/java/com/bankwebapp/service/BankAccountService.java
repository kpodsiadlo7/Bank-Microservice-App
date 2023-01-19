package com.bankwebapp.service;

import com.bankwebapp.domain.BankAccount;
import com.bankwebapp.domain.User;
import com.bankwebapp.domain.UserAccount;
import com.bankwebapp.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.TreeSet;

@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    BankAccountService(final BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }
    public TreeSet<BankAccount> getAllUserAccounts(final User user) {
        return bankAccountRepository.findAllByUserAccount(user.getUserAccount());
    }
}
