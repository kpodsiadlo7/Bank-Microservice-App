package com.bankwebapp.domain;

import com.bankwebapp.security.Authority;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Transient
    private String confirmPassword;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserAccount userAccount;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Authority> authorities = new HashSet<>();
}
