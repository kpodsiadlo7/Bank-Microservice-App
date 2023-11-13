package com.creditmanager.proposal;

import com.creditmanager.credit.enums.CreditKind;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@AllArgsConstructor
class Proposal {
    private Long id;
    private Long accountId;
    private double salary;
    private double monthlyFee;
    private String proposalNumber;
    @Enumerated(EnumType.STRING)
    private CreditKind creditKind;
}
