package com.usermanager.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class User {
    private Long id;
    private String username;
    private String realName;
    private String password;
    private String confirmPassword;
}
