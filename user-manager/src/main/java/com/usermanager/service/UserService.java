package com.usermanager.service;

import com.usermanager.domain.UserEntity;
import com.usermanager.repository.adapter.AdapterUserEntityRepository;
import com.usermanager.service.data.User;
import com.usermanager.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final AdapterUserEntityRepository adapterUserRepository;
    private final UserMapper userMapper;


    /*
    private void encodeUserPassword(final User user) {
        user.setPlainPassword(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

     */

    public User getUserById(final Long userId) {
        return new User(
                7L,
                "test",
                "test",
                "test"
        );
    }

    public User createUser(final User user) {
        User userForFail = new User();
        log.info("create user start method");
        log.info(user.getUsername());
        log.info(user.getPassword());
        log.info(user.getRealName());
        switch (validateBeforeCreateUser(user)) {
            case "User already exist" -> {
                userForFail.setUsername("User already exist");
                return userForFail;
            }
            case "Invalid data" -> {
                userForFail.setUsername("Invalid data");
                return userForFail;
            }
        }
        log.info("after validation, before register");
        return registerUser(user);
    }
    public boolean checkIfUserExistInDbWithThisUsername(final String username) {
        return adapterUserRepository.existsByUsername(username);
    }

    public User loginUser(final String username) {
        UserEntity userFromDbByUsername = adapterUserRepository.findByUsername(username);
        return userMapper.mapToUserFromUserEntity(userFromDbByUsername);
    }

    private User registerUser(final User user) {
        log.info("register method start");
        UserEntity userEntity = userMapper.mapToUserEntityFromUser(user);
        adapterUserRepository.save(userEntity);
        log.info("after save db and user id: "+userEntity.getId().toString());
        return userMapper.mapToUserFromUserEntity(userEntity);
    }

    private String validateBeforeCreateUser(final User user) {
        log.info("validate before create user start method");
        return "";
    }

}
