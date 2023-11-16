package com.usermanager.user;

import org.springframework.stereotype.Service;

@Service
class UserFactory {
    public UserDto mapToUserDtoFromUser(final User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getRealName(),
                user.getPassword(),
                null
        );
    }

    User mapToUserFromUserDto(final UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getUsername(),
                userDto.getRealName(),
                userDto.getPassword(),
                userDto.getConfirmPassword()
        );
    }

    UserEntity mapToUserEntityFromUser(final User user) {
        return new UserEntity(
                user.getUsername(),
                user.getRealName(),
                user.getPassword()
        );
    }

    UserDto buildUserDtoFromUserEntity(final UserEntity userEntity) {
        return UserDto.builder()
                .withId(userEntity.getId())
                .withUsername(userEntity.getUsername())
                .withRealName(userEntity.getRealName())
                .withPassword(userEntity.getPassword())
                .withConfirmPassword(null).build();
    }
}
