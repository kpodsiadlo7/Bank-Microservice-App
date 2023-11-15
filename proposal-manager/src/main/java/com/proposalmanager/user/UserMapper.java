package com.proposalmanager.user;

import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public User mapToUserFromUserDto(final UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getRealName()
        );
    }
}
