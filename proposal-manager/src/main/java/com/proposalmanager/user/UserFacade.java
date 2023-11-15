package com.proposalmanager.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFacade {

    private final FeignServiceUserManager feignServiceUserManager;

    public UserDto fetchUserById(final Long userId, final UserDto error) {
        log.warn("fetch user by id");
        try {
            return feignServiceUserManager.getUserById(userId);
        } catch (Exception e) {
            log.warn("problem with connecting to user manager");
            error.setId(-1L);
            error.setRealName("There is a problem with connecting to user manager");
            return error;
        }
    }
}
