package com.usermanager.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "users_db")
public class UserEntity {
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