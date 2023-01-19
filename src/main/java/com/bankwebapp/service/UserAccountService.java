package com.bankwebapp.service;

import com.bankwebapp.domain.User;
import com.bankwebapp.domain.UserAccount;
import com.bankwebapp.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.util.TreeSet;

@Service
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;

    UserAccountService(final UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }
}
