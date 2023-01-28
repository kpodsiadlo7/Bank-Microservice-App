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
    public User validateData(final User user){
        User errorUser = new User();
        if (user.getPassword() == null || user.getConfirmPassword() == null){
            errorUser.setUsername("Fill password fields");
            return errorUser;
        }
        if (user.getRealName() == null){
            errorUser.setUsername("Enter name");
            return errorUser;
        }
        if (user.getUsername() == null){
            errorUser.setUsername("Enter login");
            return errorUser;
        }
        if (checkIfUserExistInDbWithThisUsername(user.getUsername())) {
            errorUser.setUsername("User already exist!");
            return errorUser;
        }
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
        log.info("after save and user id: " + userEntity.getId().toString());
        return userMapper.mapToUserFromUserEntity(userEntity);
    }
}
