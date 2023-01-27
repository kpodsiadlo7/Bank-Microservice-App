package com.usermanager.service;

import com.usermanager.adapter.AdapterUserEntityRepository;
import com.usermanager.service.data.User;
import com.usermanager.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AdapterUserEntityRepository adapterUserRepository;
    private final UserMapper userMapper;

    public User getUserById(final Long userId) {
        return new User(
                7L,
                "test",
                "test",
                "test",
                "test"
        );
    }

    public User createUser(final User user) {
        User userForFail = new User();
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
        return registerUser(user);
    }

    private User registerUser(final User user) {
        return userMapper.mapToUserFromUserEntity(adapterUserRepository.save(userMapper.mapToUserEntityFromUser(user)));
    }

    private String validateBeforeCreateUser(final User user) {
        if (adapterUserRepository.existsByUsernameAndPlainPassword(user.getUsername(), user.getPlainPassword()))
            return "User already exist";
        if (user.getUsername() == null || user.getUsername().isEmpty() ||
                user.getRealName() == null || user.getRealName().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty() ||
                user.getPlainPassword() == null || user.getPlainPassword().isEmpty())
            return "Invalid data";
        return "";
    }
}
