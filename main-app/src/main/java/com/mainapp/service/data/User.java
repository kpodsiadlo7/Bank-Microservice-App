package com.mainapp.service.data;

import com.mainapp.security.AuthorityEntity;
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
    private Long id;
    private String username;
    private String realName;
    private String password;
    private String confirmPassword;
    private Set<AuthorityEntity> authorities = new HashSet<>();
    @OrderBy(clause = "id")
    private Set<UserAccount> accounts = new TreeSet<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", realName='" + realName + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", authorities= " + authorities.size() +
                ", accounts= " + accounts.size() +
                '}';
    }
}
