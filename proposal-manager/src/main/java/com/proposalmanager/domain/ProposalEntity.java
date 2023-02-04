package com.proposalmanager.domain;

import com.proposalmanager.domain.enums.CreditKind;
import com.proposalmanager.domain.enums.DescriptionRejected;
import com.proposalmanager.domain.enums.StatusProposal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "proposals_db")
public class ProposalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long accountId;
    private double amountOfCredit;
    private int month;
    private double salary;
    private double interest;
    private double commission;
    private double monthlyFee;
    private String currency;
    private String currencySymbol;
    private String username;
    private String purpose;
    private String proposalNumber;
    private LocalDate applicationDate;
    private boolean acceptStatement;
    @Enumerated(EnumType.STRING)
    private StatusProposal statusProposal;
    @Enumerated(EnumType.STRING)
    private DescriptionRejected descriptionRejected;
    @Enumerated(EnumType.STRING)
    private CreditKind creditKind;

    public ProposalEntity(final Long userId, final Long accountId, final double amountOfCredit, final int month, final double salary, final double interest, final double commission, final double monthlyFee, final String currency, final String currencySymbol, final String username, final String purpose, final String proposalNumber, final LocalDate applicationDate, final boolean acceptStatement, final StatusProposal statusProposal, final DescriptionRejected descriptionRejected, final CreditKind creditKind) {
        this.userId = userId;
        this.accountId = accountId;
        this.amountOfCredit = amountOfCredit;
        this.month = month;
        this.salary = salary;
        this.interest = interest;
        this.commission = commission;
        this.monthlyFee = monthlyFee;
        this.currency = currency;
        this.currencySymbol = currencySymbol;
        this.username = username;
        this.purpose = purpose;
        this.proposalNumber = proposalNumber;
        this.applicationDate = applicationDate;
        this.acceptStatement = acceptStatement;
        this.statusProposal = statusProposal;
        this.descriptionRejected = descriptionRejected;
        this.creditKind = creditKind;
    }
}
