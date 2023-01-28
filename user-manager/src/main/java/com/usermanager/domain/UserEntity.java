package com.usermanager.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users_db")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String realName;
    private String password;
    private String plainPassword;

    public UserEntity(final String username, final String realName, final String password, final String plainPassword) {
        this.username = username;
        this.realName = realName;
        this.password = password;
        this.plainPassword = plainPassword;
    }
}