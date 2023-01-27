package com.mainapp.web.dto;

import com.mainapp.security.Authority;
import com.mainapp.service.data.UserAccounts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OrderBy;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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
    @OrderBy(clause = "id")
    private Set<UserAccounts> accounts = new TreeSet<>();
}
