package com.creditmanager.proposal.dto;

import com.creditmanager.credit.enums.CreditKind;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@AllArgsConstructor
public class ProposalDto {
    private final Long id;
    private final Long accountId;
    private final double salary;
    private final double monthlyFee;
    private final String proposalNumber;
    @Enumerated(EnumType.STRING)
    private final CreditKind creditKind;

    public static BuilderProposalDto builder(){
        return new BuilderProposalDto();
    }

    ProposalDto(final BuilderProposalDto builder) {
        this.id = builder.id;
        this.accountId = builder.accountId;
        this.salary = builder.salary;
        this.monthlyFee = builder.monthlyFee;
        this.proposalNumber = builder.proposalNumber;
        this.creditKind = builder.creditKind;
    }

    public static class BuilderProposalDto {

        private BuilderProposalDto(){
        }
        public ProposalDto build(){
            return new ProposalDto(this);
        }
        private Long id;
        private Long accountId;
        private double salary;
        private double monthlyFee;
        private String proposalNumber;
        @Enumerated(EnumType.STRING)
        private CreditKind creditKind;

        public BuilderProposalDto withId(Long id) {
            this.id = id;
            return this;
        }

        public BuilderProposalDto withAccountId(Long accountId) {
            this.accountId = accountId;
            return this;
        }

        public BuilderProposalDto withSalary(double salary) {
            this.salary = salary;
            return this;
        }

        public BuilderProposalDto withMonthlyFee(double monthlyFee) {
            this.monthlyFee = monthlyFee;
            return this;
        }

        public BuilderProposalDto withProposalNumber(String proposalNumber) {
            this.proposalNumber = proposalNumber;
            return this;
        }
        public BuilderProposalDto withCreditKind(CreditKind creditKind) {
            this.creditKind = creditKind;
            return this;
        }
    }
}
