package com.usermanager.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String realName;
    private String password;

    public UserEntity(final String username, final String realName, final String password) {
        this.username = username;
        this.realName = realName;
        this.password = password;
    }
}