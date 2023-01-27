package com.mainapp.service.data;

import com.mainapp.security.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OrderBy;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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
    @OrderBy(clause = "id")
    private Set<UserAccounts> accounts = new TreeSet<>();

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", realName='" + realName + '\'' +
                ", password='" + password + '\'' +
                ", plainPassword='" + plainPassword + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", authorities=" + authorities.size() +
                ", accounts=" + accounts.size() +
                '}';
    }
}
