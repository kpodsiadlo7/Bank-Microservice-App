package com.proposalmanager.service.mapper;

import com.proposalmanager.service.data.User;
import com.proposalmanager.web.dto.UserDto;
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
