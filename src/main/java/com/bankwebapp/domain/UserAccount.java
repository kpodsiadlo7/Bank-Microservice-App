package com.bankwebapp.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OrderBy;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Getter
@Setter
public class UserAccount implements Comparable<UserAccount> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userAccount")
    @OrderBy(clause = "id")
    private Set<BankAccount> bankAccounts = new TreeSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userAccount")
    @OrderBy(clause = "id")
    private Set<CardAccount> cardAccounts = new TreeSet<>();

    @Override
    public int compareTo(final UserAccount o) {
        return this.id.compareTo(o.getId());
    }
}
