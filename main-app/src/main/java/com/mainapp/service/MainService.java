package com.mainapp.service;

import com.mainapp.repository.adapter.AdapterAuthorityRepository;
import com.mainapp.security.AuthorityEntity;
import com.mainapp.service.data.User;
import com.mainapp.service.data.UserAccount;
import com.mainapp.service.mapper.UserAccountsMapper;
import com.mainapp.service.mapper.UserMapper;
import com.mainapp.web.feign.FeignServiceAccountsManager;
import com.mainapp.web.feign.FeignServiceUserManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {

    private final FeignServiceUserManager feignServiceUserManager;
    private final FeignServiceAccountsManager feignServiceAccountsManager;
    private final AdapterAuthorityRepository adapterAuthorityRepository;
    private final UserMapper userMapper;
    private final UserAccountsMapper userAccountsMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean createUser(final User user, final ModelMap modelMap) {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            log.info("wjechłem tutaj po error dla password");
            modelMap.put("error", "Password doesn't match!");
            return false;
        }

        //creating new user and returning him using external application user-manager
        User userFromUserManager = createAndReturnNewUser(user);

        if (userFromUserManager.getId() == null) {
            modelMap.put("error", userFromUserManager.getUsername());
            return false;
        }

        try {
            //creating, returning and set main account for new user using external application accounts-manager
            userFromUserManager.setAccounts(Set.of(createAccountForUser(userFromUserManager.getId(), new UserAccount())));
        } catch (Exception e) {
            modelMap.put("error", "Error with creating account");
        }

        User afterAuthority = setAuthorityForUser(userFromUserManager);
        log.info(afterAuthority.toString());

        Authentication authentication = new UsernamePasswordAuthenticationToken
                (afterAuthority, null, afterAuthority.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }

    public User createAndReturnNewUser(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.mapToUserFromUserDto
                (feignServiceUserManager.createUser(userMapper.mapToUserDtoFromUser(user)));
    }


    public User setAuthorityForUser(final User userFromUserManager) {
        AuthorityEntity authorityEntity = new AuthorityEntity();
        authorityEntity.setAuthority("ROLE_USER");
        authorityEntity.setUserId(userFromUserManager.getId());
        authorityEntity.setUserPassword(userFromUserManager.getPassword());
        adapterAuthorityRepository.save(authorityEntity);
        userFromUserManager.getAuthorities().add(authorityEntity);
        return userFromUserManager;
    }

    public UserAccount createAccountForUser(final Long userId, final UserAccount userAccount) {
        return userAccountsMapper.mapToUserAccountFromUserAccountDto(feignServiceAccountsManager.createAccountForUser
                (userId, userAccountsMapper.mapToUserAccountDtoFromUserAccount(userAccount)));
    }
}
