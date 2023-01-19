package com.bankwebapp.service;

import com.bankwebapp.domain.BankAccount;
import com.bankwebapp.domain.User;
import com.bankwebapp.domain.UserAccount;
import com.bankwebapp.repository.UserRepository;
import com.bankwebapp.security.Authority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.thymeleaf.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean validUserToCreateUser(User user, ModelMap modelMap, BankAccount bankAccount) {
        if (StringUtils.isEmpty(user.getUsername()) ||
                StringUtils.isEmpty(user.getPassword()) ||
                StringUtils.isEmpty(user.getConfirmPassword()) ||
                StringUtils.isEmpty(user.getRealName())) {
            modelMap.put("error", "You must fill all fields!");
            return false;
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            modelMap.put("error", "Passwords don't match");
            return false;
        }

        if (userRepository.existsByPlainPasswordAndUsername(user.getPassword(), user.getUsername())) {
            modelMap.put("error", "User with this login and password already exist!");
            return false;
        }
        user.setUserAccount(createUserAccount(bankAccount,user));
        user.setPlainPassword(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(saveUser(user));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }

    private UserAccount createUserAccount(BankAccount bankAccount,User user) {
        UserAccount userAccount = new UserAccount();
        userAccount.setBankAccounts(Set.of(createBankAccount(bankAccount,userAccount)));
        userAccount.setUser(user);
        return userAccount;
    }

    private BankAccount createBankAccount(BankAccount bankAccount, UserAccount userAccount) {
        BankAccount account = new BankAccount();
        account.setNumber(randomNumber());
        account.setBalance(new BigDecimal(0));
        account.setUserAccount(userAccount);
        try {
            if (bankAccount.getName().equals(""));
        } catch (NullPointerException e){
            account.setCurrency("PLN");
            account.setName("Main Account");
            return account;
        }
        account.setName(bankAccount.getName());
        account.setCurrency(bankAccount.getCurrency());
        return account;
    }

    public String randomNumber() {
        Random random = new Random();
        StringBuilder b = new StringBuilder();
        for (int i = 0; i <= 20; i++) {
            b.append(random.nextInt(7));
        }
        return b.toString();
    }

    private User saveUser(final User user) {
        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");
        authority.setUser(user);

        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);

        user.setAuthorities(authorities);
        return user;
    }
}
