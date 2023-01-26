package com.mainapp.web.dto;

import com.mainapp.security.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String username;
    private String realName;
    private String password;
    private String plainPassword;
    private String confirmPassword;
    private Set<Authority> authorities = new HashSet<>();
}
