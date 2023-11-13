package com.creditmanager.credit;

import com.creditmanager.proposal.ProposalDto;
import org.springframework.stereotype.Service;

@Service
class CreditFactory {
    CreditEntity toCreditEntityFromCredit(final Credit credit) {
        return new CreditEntity(
                credit.getUserId(),
                credit.getAccountId(),
                credit.getProposalNumber(),
                credit.getCreditKind()
        );
    }
    Credit buildCreditFromProposalDto(final ProposalDto proposalDto){
        return Credit.builder()
                .withId(proposalDto.getId())
                .withAccountId(proposalDto.getAccountId())
                .withProposalNumber(proposalDto.getProposalNumber())
                .withCreditKind(proposalDto.getCreditKind()).build();
    }
}
