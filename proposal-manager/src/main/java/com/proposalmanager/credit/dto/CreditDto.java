package com.proposalmanager.credit.dto;

import com.proposalmanager.credit.enums.CreditKind;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
public class CreditDto {
    private final Long id;
    private final Long userId;
    private final Long accountId;
    private final String proposalNumber;
    @Enumerated(EnumType.STRING)
    private final CreditKind creditKind;

    public CreditDto(final BuilderCreditDto builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.accountId = builder.accountId;
        this.proposalNumber = builder.proposalNumber;
        this.creditKind = builder.creditKind;
    }
    public static BuilderCreditDto builder(){
        return new BuilderCreditDto();
    }

    public BuilderCreditDto toBuilder(){
        return new BuilderCreditDto()
                .withId(id)
                .withUserId(userId)
                .withAccountId(accountId)
                .withProposalNumber(proposalNumber)
                .withCreditKind(creditKind);
    }

    public static class BuilderCreditDto{
        private BuilderCreditDto(){}
        public CreditDto build(){
            return new CreditDto(this);
        }
        private Long id;
        private Long userId;
        private Long accountId;
        private String proposalNumber;
        @Enumerated(EnumType.STRING)
        private CreditKind creditKind;

        public BuilderCreditDto withId(Long id) {
            this.id = id;
            return this;
        }

        public BuilderCreditDto withUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public BuilderCreditDto withAccountId(Long accountId) {
            this.accountId = accountId;
            return this;
        }

        public BuilderCreditDto withProposalNumber(String proposalNumber) {
            this.proposalNumber = proposalNumber;
            return this;
        }

        public BuilderCreditDto withCreditKind(CreditKind creditKind) {
            this.creditKind = creditKind;
            return this;
        }
    }
}
