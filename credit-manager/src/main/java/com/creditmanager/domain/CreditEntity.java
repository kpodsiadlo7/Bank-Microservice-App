package com.creditmanager.domain;

import com.creditmanager.domain.enums.CreditKind;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "credits_db")
public class CreditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long accountId;
    private String proposalNumber;
    @Enumerated(EnumType.STRING)
    private CreditKind creditKind;

    public CreditEntity(final Long userId, final Long accountId, final String proposalNumber, final CreditKind creditKind) {
        this.userId = userId;
        this.accountId = accountId;
        this.proposalNumber = proposalNumber;
        this.creditKind = creditKind;
    }
}
