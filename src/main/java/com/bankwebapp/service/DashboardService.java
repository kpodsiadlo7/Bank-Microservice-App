package com.bankwebapp.service;

import com.bankwebapp.domain.BankAccount;
import com.bankwebapp.domain.User;
import com.bankwebapp.domain.UserAccount;
import org.springframework.stereotype.Service;

import java.util.TreeSet;

@Service
public class DashboardService {

    private final BankAccountService bankAccountService;

    DashboardService(final BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    public TreeSet<BankAccount> getAllUserAccountsByUserId(final User user) {
        return bankAccountService.getAllUserAccounts(user);
    }
}

