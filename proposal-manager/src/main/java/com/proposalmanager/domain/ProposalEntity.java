package com.proposalmanager.domain;

import com.proposalmanager.domain.enums.StatusApplication;
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
    private float amountOfCredit;
    private int month;
    private float salary;
    private double interest;
    private double commission;
    private BigDecimal monthlyFee;
    private String currency;
    private String currencySymbol;
    private String username;
    private String purpose;
    private String applicationNumber;
    private LocalDate applicationDate;
    @Enumerated(EnumType.STRING)
    private StatusApplication statusApplication;
}
