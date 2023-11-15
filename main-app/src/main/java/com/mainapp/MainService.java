package com.mainapp;

import com.mainapp.account.AccountFacade;
import com.mainapp.account.dto.AccountDto;
import com.mainapp.security.AdapterAuthorityRepository;
import com.mainapp.security.AuthorityEntity;
import com.mainapp.transaction.TransactionDto;
import com.mainapp.transaction.TransactionFacade;
import com.mainapp.transfer.TransferDto;
import com.mainapp.user.FeignServiceUserManager;
import com.mainapp.user.User;
import com.mainapp.user.UserMapper;
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
    private final TransactionFacade transactionFacade;
    private final AdapterAuthorityRepository adapterAuthorityRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    private final AccountFacade accountFacade;

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
            userFromUserManager.setAccounts
                    (Set.of(createAccountForUser(userFromUserManager.getId(),
                            accountFacade.getNewEmptyAccount())));
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

    public AccountDto createAccountForUser(final Long userId, final AccountDto accountDto) {
        return accountFacade.createAccountForUser(userId,accountDto);
    }

    public boolean makeTransaction(final User user, final TransferDto transferDto, final ModelMap modelMap,
                                   final String descriptionTransaction, final Long thisAccountId) {
        log.info("make transaction");
        try {
            TransactionDto returningTransactionDto =
                    transactionFacade.makeTransaction(user.getId(), thisAccountId, descriptionTransaction, transferDto);
            if (returningTransactionDto.getKindTransaction().equals("error")) {
                modelMap.put("error", returningTransactionDto.getDescription());
                return false;
            }
        } catch (Exception e) {
            modelMap.put("error", "There was an error with trying to connect with transactional service");
            try {
                modelMap.put("userBankAccounts", accountFacade.getAllAccountsByUserId(user.getId()));
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
