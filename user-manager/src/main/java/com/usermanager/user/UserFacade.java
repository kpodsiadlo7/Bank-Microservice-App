package com.usermanager.user;

import com.usermanager.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFacade {

    private final AdapterUserEntityRepository adapterUserRepository;
    private final UserFactory userFactory;
    public UserDto validateData(final User user){
        if(user == null) {
            log.error("user is invalid");
            return UserDto.builder().withUsername("Critical error").build();
        }
        if (user.getPassword() == null || user.getConfirmPassword() == null ||
                user.getPassword().isEmpty() || user.getConfirmPassword().isEmpty()){
            log.warn("error, field password");
            return UserDto.builder().withUsername("Fill password fields").build();
        }
        if (user.getRealName() == null || user.getRealName().isEmpty()){
            log.warn("error, enter name");
            return UserDto.builder().withUsername("Enter name").build();
        }
        if (user.getUsername() == null || user.getUsername().isEmpty()){
            log.warn("error, enter login");
            return UserDto.builder().withUsername("Enter login").build();
        }
        if (checkIfUserExistInDbWithThisUsername(user.getUsername())) {
            log.warn("user exist");
            return UserDto.builder().withUsername("Try again!").build();
        }
        return registerUser(user);
    }

    public boolean checkIfUserExistInDbWithThisUsername(final String username) {
        log.warn("check if user exist in db with this username");
        return adapterUserRepository.existsByUsername(username);
    }

    public UserDto loginUser(final String username) {
        log.warn("login user");
        if(adapterUserRepository.existsByUsername(username)){
            UserEntity userFromDbByUsername = adapterUserRepository.findByUsername(username);
            return userFactory.buildUserDtoFromUserEntity(userFromDbByUsername);
        }
        return UserDto.builder().withUsername("User doesn't exist!").build();
    }

    private UserDto registerUser(final User user) {
        log.info("register method start");
        UserEntity userEntity = userFactory.mapToUserEntityFromUser(user);
        adapterUserRepository.save(userEntity);
        return userFactory.buildUserDtoFromUserEntity(userEntity);
    }

    public UserDto getUserById(final Long userId) {
        log.warn("get user by id");
        return userFactory.buildUserDtoFromUserEntity(adapterUserRepository.findById(userId));
    }
}
