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

import java.math.BigDecimal;

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
        if (!user.getPassword().equals(user.getConfirmPassword()))
            return false;
        user.setPlainPassword(user.getConfirmPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User userFromUserManager = userMapper.mapToUserFromUserDto
                (feignServiceUserManager.createUser(userMapper.mapToUserDtoFromUser(user)));
        log.info(user.getUsername());
        log.info("user id" + userFromUserManager.getId().toString());
        if (userFromUserManager.getId() != null) {
            createMainAccountForUser(userFromUserManager.getId());
        } else
            modelMap.put("error", returnCurrentErrorStatement(user.getUsername()));

        Authentication authentication = new UsernamePasswordAuthenticationToken
                (setAuthorityForUser(userFromUserManager), null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }

    private String returnCurrentErrorStatement(final String username) {
        switch (username) {
            case "Password doesn't match!" -> {
                return "Password doesn't match!";
            }
            case "Invalid data" -> {
                return "Invalid Data";
            }
            case "User already exist!" -> {
                return "User already exist!";
            }
        }
        return "";
    }

    private User setAuthorityForUser(final User userFromUserManager) {
        AuthorityEntity authorityEntity = new AuthorityEntity();
        authorityEntity.setAuthority("ROLE_USER");
        authorityEntity.setUserId(userFromUserManager.getId());
        adapterAuthorityRepository.save(authorityEntity);
        return userFromUserManager;
    }

    private void createMainAccountForUser(final Long userId) {
        UserAccount userAccount = new UserAccount(
                null,
                "Main Account",
                new BigDecimal(0),
                "",
                "PLN",
                "zł"
        );
        feignServiceAccountsManager.createAccountForUser
                (userId, userAccountsMapper.mapToUserAccountDtoFromUserAccount(userAccount));
    }
}
