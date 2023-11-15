package com.mainapp.user;

import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public User mapToUserFromUserDto(final UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getUsername(),
                userDto.getRealName(),
                userDto.getPassword(),
                userDto.getConfirmPassword(),
                userDto.getAuthorities(),
                userDto.getAccounts()
        );
    }

    UserDto mapToUserDtoFromUser(final User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getRealName(),
                user.getPassword(),
                user.getConfirmPassword(),
                user.getAuthorities(),
                user.getAccounts()
        );
    }
}
