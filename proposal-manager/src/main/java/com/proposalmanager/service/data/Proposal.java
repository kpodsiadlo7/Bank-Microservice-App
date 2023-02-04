package com.proposalmanager.service.data;

import com.proposalmanager.domain.enums.StatusProposal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Proposal {
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
    private String proposalNumber;
    private LocalDate applicationDate;
    private boolean acceptStatement;
    private StatusProposal statusProposal;
}
