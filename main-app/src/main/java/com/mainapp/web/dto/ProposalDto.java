package com.mainapp.web.dto;

import com.mainapp.service.data.enums.CreditKind;
import com.mainapp.service.data.enums.DescriptionRejected;
import com.mainapp.service.data.enums.StatusProposal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProposalDto {
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
}
