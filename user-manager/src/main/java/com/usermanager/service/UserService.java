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
        return new User();
    }

    public String createUser(final User user) {
        switch (validateBeforeCreateUser(user)) {
            case "User already exist":
                return "User already exist";
            case "Invalid data":
                return "Invalid data";
        }
        registerUser(user);
        return "ok";
    }

    private void registerUser(final User user) {
        adapterUserRepository.save(userMapper.mapToUserEntityFromUser(user));
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
