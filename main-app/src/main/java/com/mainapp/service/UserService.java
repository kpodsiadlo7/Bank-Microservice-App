package com.mainapp.service;

import com.mainapp.repository.adapter.AdapterAuthorityRepository;
import com.mainapp.security.AuthorityEntity;
import com.mainapp.service.data.User;
import com.mainapp.service.data.UserAccount;
import com.mainapp.service.mapper.UserAccountsMapper;
import com.mainapp.service.mapper.UserMapper;
import com.mainapp.web.dto.UserAccountDto;
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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final FeignServiceUserManager feignServiceUserManager;
    private final FeignServiceAccountsManager feignServiceAccountsManager;
    private final AdapterAuthorityRepository adapterAuthorityRepository;
    private final UserMapper userMapper;
    private final UserAccountsMapper userAccountsMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean createUser(final User user, final ModelMap modelMap) {
        if (!user.getPassword().equals(user.getConfirmPassword())){
            modelMap.put("error","Password doesn't match!");
            return false;
        }
        if (checkIfUserAlreadyExist(user.getUsername())) {
            modelMap.put("error","User with this login already exist!");
            return false;
        }
        User userFromUserManager = createAndReturnNewUser(user);

        if (userFromUserManager.getId() != null)
            userFromUserManager.setAccounts(Set.of(createMainAccountForUser(userFromUserManager.getId())));
        User afterAuthority = setAuthorityForUser(userFromUserManager);

        log.info(afterAuthority.toString());

        Authentication authentication = new UsernamePasswordAuthenticationToken
                (afterAuthority, null, afterAuthority.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }

    private User createAndReturnNewUser(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.mapToUserFromUserDto
                (feignServiceUserManager.createUser(userMapper.mapToUserDtoFromUser(user)));
    }


    private User setAuthorityForUser(final User userFromUserManager) {
        AuthorityEntity authorityEntity = new AuthorityEntity();
        authorityEntity.setAuthority("ROLE_USER");
        authorityEntity.setUserId(userFromUserManager.getId());
        authorityEntity.setUserPassword(userFromUserManager.getPassword());
        adapterAuthorityRepository.save(authorityEntity);
        userFromUserManager.getAuthorities().add(authorityEntity);
        return userFromUserManager;
    }

    private UserAccount createMainAccountForUser(final Long userId) {
        UserAccount userAccount = new UserAccount(
                null,
                "Main Account",
                new BigDecimal(0),
                "",
                "PLN",
                "zł"
        );
        return userAccountsMapper.mapToUserAccountFromUserAccountDto(feignServiceAccountsManager.createAccountForUser
                (userId, userAccountsMapper.mapToUserAccountDtoFromUserAccount(userAccount)));
    }

    /**
     * Before create user, we check in database if user with this login already exist
     */
    private boolean checkIfUserAlreadyExist(final String username) {
        return feignServiceUserManager.checkUser(username);
    }
}
