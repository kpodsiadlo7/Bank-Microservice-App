package com.usermanager.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "userEntity")
    private Set<AccountEntity> accounts = new HashSet<>();
}