package com.mainapp.service;

import com.mainapp.repository.adapter.AdapterAuthorityRepository;
import com.mainapp.security.AuthorityEntity;
import com.mainapp.service.data.Account;
import com.mainapp.service.data.User;
import com.mainapp.service.mapper.AccountMapper;
import com.mainapp.service.mapper.UserMapper;
import com.mainapp.web.dto.TransactionDto;
import com.mainapp.web.dto.TransferDto;
import com.mainapp.web.feign.FeignServiceAccountsManager;
import com.mainapp.web.feign.FeignServiceTransactionsManager;
import com.mainapp.web.feign.FeignServiceUserManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {

    private final FeignServiceUserManager feignServiceUserManager;
    private final FeignServiceAccountsManager feignServiceAccountsManager;
    private final FeignServiceTransactionsManager feignServiceTransactionsManager;
    private final AdapterAuthorityRepository adapterAuthorityRepository;
    private final UserMapper userMapper;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(final User user, final ModelMap modelMap) {
        log.info("create user");
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            log.warn("password doesnt match");
            modelMap.put("error", "Password doesn't match!");
            return false;
        }

        //creating new user and returning him
        User userFromUserManager = createAndReturnNewUser(user);

        if (userFromUserManager.getId() == null) {
            log.warn("error " + userFromUserManager.getUsername());
            modelMap.put("error", userFromUserManager.getUsername());
            return false;
        }

        try {
            //creating, returning and set main account for new user
            userFromUserManager.setAccounts(Set.of(createAccountForUser(userFromUserManager.getId(), new Account())));
        } catch (Exception e) {
            log.warn("error with creating account");
            modelMap.put("error", "Error with creating account");
        }

        User afterAuthority = setAuthorityForUser(userFromUserManager);

        Authentication authentication = new UsernamePasswordAuthenticationToken
                (afterAuthority, null, afterAuthority.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("successful create and authority for user");
        return true;
    }

    User createAndReturnNewUser(final User user) {
        log.info("create and return new user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.mapToUserFromUserDto
                (feignServiceUserManager.createUser(userMapper.mapToUserDtoFromUser(user)));
    }


    User setAuthorityForUser(final User userFromUserManager) {
        log.info("set authority for user");
        AuthorityEntity authorityEntity = new AuthorityEntity();
        authorityEntity.setAuthority("ROLE_USER");
        authorityEntity.setUserId(userFromUserManager.getId());
        authorityEntity.setUserPassword(userFromUserManager.getPassword());
        adapterAuthorityRepository.save(authorityEntity);
        userFromUserManager.getAuthorities().add(authorityEntity);
        return userFromUserManager;
    }

    public Account createAccountForUser(final Long userId, final Account account) {
        log.info("create account for user");
        return accountMapper.mapToUserAccountFromUserAccountDto(feignServiceAccountsManager.createAccountForUser
                (userId, accountMapper.mapToUserAccountDtoFromUserAccount(account)));
    }

    public boolean makeTransaction(final User user, final TransferDto transferDto, final ModelMap modelMap,
                                   final String descriptionTransaction, final Long thisAccountId) {
        log.info("make transaction");
        try {
            TransactionDto returningTransactionDto =
                    feignServiceTransactionsManager.makeTransaction(user.getId(), thisAccountId, descriptionTransaction, transferDto);
            if (returningTransactionDto.getKindTransaction().equals("error")) {
                modelMap.put("error", returningTransactionDto.getDescription());
                return false;
            }
        } catch (Exception e) {
            modelMap.put("error", "There was an error with trying to connect with transactional service");
            try {
                modelMap.put("userBankAccounts", feignServiceAccountsManager.getAllAccountsByUserId(user.getId()));
            } catch (Exception b) {
                modelMap.put("error", "There was an error fetching your accounts");
            }
            modelMap.put("quickTransfer", new TransferDto());
            return false;
        }
        log.info("successful make transaction");
        return true;
    }
}
