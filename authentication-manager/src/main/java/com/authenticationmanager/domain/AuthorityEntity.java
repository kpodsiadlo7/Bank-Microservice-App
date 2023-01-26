package com.authenticationmanager.domain;

import javax.persistence.*;

@Entity
@Table(name = "authorities_db")
public class AuthorityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;
}
