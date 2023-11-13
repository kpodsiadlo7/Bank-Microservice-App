package com.creditmanager.proposal;

import org.springframework.stereotype.Service;

@Service
class ProposalFactory {
    ProposalDto buildProposalDtoFromProposal(final Proposal proposal){
        return ProposalDto.builder()
                .withSalary(proposal.getSalary())
                .withMonthlyFee(proposal.getMonthlyFee()).build();
    }

    Proposal fromPorposalDtoToProposal(final ProposalDto proposalDto){
        return new Proposal(
                proposalDto.getId(),
                proposalDto.getAccountId(),
                proposalDto.getSalary(),
                proposalDto.getMonthlyFee(),
                proposalDto.getProposalNumber(),
                proposalDto.getCreditKind()
        );
    }
}
