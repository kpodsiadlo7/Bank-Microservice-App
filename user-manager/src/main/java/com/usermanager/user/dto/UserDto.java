package com.usermanager.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {
    private final Long id;
    private final String username;
    private final String realName;
    private final String password;
    private final String confirmPassword;

    private UserDto(final BuilderUserDto builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.realName = builder.realName;
        this.password = builder.password;
        this.confirmPassword = builder.confirmPassword;
    }

    public static BuilderUserDto builder(){
        return new BuilderUserDto();
    }

    public static class BuilderUserDto{
        private BuilderUserDto(){}
        public UserDto build(){
            return new UserDto(this);
        }
        private Long id;
        private String username;
        private String realName;
        private String password;
        private String confirmPassword;

        public BuilderUserDto withId(Long id) {
            this.id = id;
            return this;
        }

        public BuilderUserDto withUsername(String username) {
            this.username = username;
            return this;
        }

        public BuilderUserDto withRealName(String realName) {
            this.realName = realName;
            return this;
        }

        public BuilderUserDto withPassword(String password) {
            this.password = password;
            return this;
        }

        public BuilderUserDto withConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
            return this;
        }
    }
}
