package com.creditmanager.credit;

import com.creditmanager.credit.enums.CreditKind;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@AllArgsConstructor
class Credit {
    private final Long id;
    private final Long userId;
    private final Long accountId;
    private final String proposalNumber;
    @Enumerated(EnumType.STRING)
    private final CreditKind creditKind;

    Credit(final BuilderCredit builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.accountId = builder.accountId;
        this.proposalNumber = builder.proposalNumber;
        this.creditKind = builder.creditKind;
    }

    public static BuilderCredit builder(){
        return new BuilderCredit();
    }

    public static class BuilderCredit{
        private BuilderCredit(){
        }
        Credit build(){
            return new Credit(this);
        }
        private Long id;
        private Long userId;
        private Long accountId;
        private String proposalNumber;
        @Enumerated(EnumType.STRING)
        private CreditKind creditKind;

        public BuilderCredit withId(Long id) {
            this.id = id;
            return this;
        }
        public BuilderCredit withUserId(Long userId) {
            this.userId = userId;
            return this;
        }
        public BuilderCredit withAccountId(Long accountId) {
            this.accountId = accountId;
            return this;
        }
        public BuilderCredit withProposalNumber(String proposalNumber) {
            this.proposalNumber = proposalNumber;
            return this;
        }
        public BuilderCredit withCreditKind(CreditKind creditKind) {
            this.creditKind = creditKind;
            return this;
        }
    }
}
