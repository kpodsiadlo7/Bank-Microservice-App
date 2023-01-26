package com.usermanager.service.mapper;

import com.usermanager.domain.UserEntity;
import com.usermanager.service.data.User;
import com.usermanager.web.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserDto mapToUserDtoFromUser(final User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getRealName(),
                user.getPassword(),
                user.getPlainPassword()
        );
    }

    public User mapToUserFromUserDto(final UserDto userDto) {
        return new User(
                userDto.getUserId(),
                userDto.getUsername(),
                userDto.getRealName(),
                userDto.getPassword(),
                userDto.getPlainPassword()
        );
    }

    public UserEntity mapToUserEntityFromUser(final User user) {
        return new UserEntity(
                user.getId(),
                user.getUsername(),
                user.getRealName(),
                user.getPassword(),
                user.getPlainPassword()
        );
    }
}
