package com.mainapp.user;

import com.mainapp.account.AccountFacade;
import com.mainapp.account.dto.AccountDto;
import com.mainapp.security.AuthorityEntity;
import com.mainapp.security.SecurityFacade;
import com.mainapp.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFacade {
    private final UserMapper userMapper;
    private final AccountFacade accountFacade;
    private final PasswordEncoder passwordEncoder;
    private final SecurityFacade securityFacade;
    private final FeignServiceUserManager feignServiceUserManager;

    public User createAndReturnNewUser(User user) {
        log.info("create and return new user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userFromUserManager = userMapper.mapToUserFromUserDto
                (feignServiceUserManager.createUser(userMapper.mapToUserDtoFromUser(user)));
        if(userFromUserManager.getUsername().equals("User already exist!")){
            log.info("User already exist! (UserFacade)");
            return userFromUserManager;
        }
        log.info("user returned from user-manager");
        userFromUserManager.setAccounts(Set.of(createAccountForUser(userFromUserManager, AccountDto.builder().build())));

        // Security user
        User authorityUser = setAuthorityForUser(userFromUserManager);
        securityFacade.setAuthentication(authorityUser);
        return userFromUserManager;
    }

    User setAuthorityForUser(User userFromUserManager) {
        log.info("set authority");
        AuthorityEntity authorityEntity = new AuthorityEntity();
        authorityEntity.setAuthority("ROLE_USER");
        authorityEntity.setUserId(userFromUserManager.getId());
        authorityEntity.setUserPassword(userFromUserManager.getPassword());
        if (securityFacade != null) {
            securityFacade.saveAuthentication(authorityEntity);
            log.info("securityFacade is valid");
        }
        userFromUserManager.getAuthorities().add(authorityEntity);
        return userFromUserManager;
    }

    public User mapToUserFromUserDto(final UserDto userDto) {
        return userMapper.mapToUserFromUserDto(userDto);
    }

    public UserDto mapToUserDtoFromUser(final User user) {
        return userMapper.mapToUserDtoFromUser(user);
    }

    public AccountDto createAccountForUser(final User user, final AccountDto accountDto) {
        log.info("createAccountForUser");
        return accountFacade.createAccountForUser(user.getId(),accountDto);
    }
}
