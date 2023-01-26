package com.mainapp.service.data;

import com.mainapp.security.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long userId;
    private String username;
    private String realName;
    private String password;
    private String plainPassword;
    private String confirmPassword;
    private Set<Authority> authorities = new HashSet<>();
}
