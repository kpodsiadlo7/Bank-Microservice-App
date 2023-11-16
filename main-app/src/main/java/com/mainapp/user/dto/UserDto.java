package com.mainapp.user.dto;

import com.mainapp.account.dto.AccountDto;
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
public class UserDto {
    private Long id;
    private String username;
    private String realName;
    private String password;
    private String confirmPassword;
    private Set<AuthorityEntity> authorities = new HashSet<>();
    @OrderBy(clause = "id")
    private Set<AccountDto> accounts = new TreeSet<>();
}
