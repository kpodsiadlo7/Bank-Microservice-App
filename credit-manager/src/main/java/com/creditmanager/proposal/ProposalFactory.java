package com.creditmanager.proposal;

class ProposalFactory {

    ProposalDto buildProposalDtoFromProposal(final Proposal proposal){
        return ProposalDto.builder()
                .withSalary(proposal.getSalary())
                .withMonthlyFee(proposal.getMonthlyFee()).build();
    }
}
