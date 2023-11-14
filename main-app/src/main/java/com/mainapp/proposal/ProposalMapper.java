package com.mainapp.proposal;

import org.springframework.stereotype.Service;

@Service
public class ProposalMapper {

    public ProposalDto mapToProposalDtoFromProposal(final Proposal proposal) {
        return new ProposalDto(
                proposal.getId(),
                proposal.getUserId(),
                proposal.getAccountId(),
                proposal.getAmountOfCredit(),
                proposal.getMonth(),
                proposal.getSalary(),
                proposal.getInterest(),
                proposal.getCommission(),
                proposal.getMonthlyFee(),
                proposal.getCurrency(),
                proposal.getCurrencySymbol(),
                proposal.getUsername(),
                proposal.getPurpose(),
                proposal.getProposalNumber(),
                proposal.getDateFrom(),
                proposal.getDateTo(),
                proposal.isAcceptStatement(),
                proposal.getStatusProposal(),
                proposal.getDescriptionRejected(),
                proposal.getCreditKind()
        );
    }

    public Proposal mapToProposalFromProposalDto(final ProposalDto proposalDto) {
        return new Proposal(
                proposalDto.getId(),
                proposalDto.getUserId(),
                proposalDto.getAccountId(),
                proposalDto.getAmountOfCredit(),
                proposalDto.getMonth(),
                proposalDto.getSalary(),
                proposalDto.getInterest(),
                proposalDto.getCommission(),
                proposalDto.getMonthlyFee(),
                proposalDto.getCurrency(),
                proposalDto.getCurrencySymbol(),
                proposalDto.getUsername(),
                proposalDto.getPurpose(),
                proposalDto.getProposalNumber(),
                proposalDto.getDateFrom(),
                proposalDto.getDateTo(),
                proposalDto.isAcceptStatement(),
                proposalDto.getStatusProposal(),
                proposalDto.getDescriptionRejected(),
                proposalDto.getCreditKind()
        );
    }
}
