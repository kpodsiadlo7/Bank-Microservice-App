package com.bankwebapp.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OrderBy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Getter
@Setter
public class BankAccount implements Comparable<BankAccount>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal balance;
    private String number;
    private String currency;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bankAccount")
    @OrderBy(clause = "id")
    private Set<Transaction> transactions = new TreeSet<>();
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private UserAccount userAccount;

    @Override
    public int compareTo(final BankAccount o) {
        return this.id.compareTo(o.getId());
    }
}
