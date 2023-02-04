package com.proposalmanager.service.mapper;

import com.proposalmanager.domain.ProposalEntity;
import com.proposalmanager.service.data.Proposal;
import com.proposalmanager.web.dto.ProposalDto;
import org.springframework.stereotype.Service;

@Service
public class ProposalMapper {

    public ProposalDto mapToProposalDtoFromProposal(final Proposal proposal){
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
                proposal.getApplicationDate(),
                proposal.isAcceptStatement(),
                proposal.getStatusProposal(),
                proposal.getDescriptionRejected(),
                proposal.getCreditKind()
        );
    }

    public Proposal mapToProposalFromProposalEntity(final ProposalEntity proposalEntity) {
        return new Proposal(
                proposalEntity.getId(),
                proposalEntity.getUserId(),
                proposalEntity.getAccountId(),
                proposalEntity.getAmountOfCredit(),
                proposalEntity.getMonth(),
                proposalEntity.getSalary(),
                proposalEntity.getInterest(),
                proposalEntity.getCommission(),
                proposalEntity.getMonthlyFee(),
                proposalEntity.getCurrency(),
                proposalEntity.getCurrencySymbol(),
                proposalEntity.getUsername(),
                proposalEntity.getPurpose(),
                proposalEntity.getProposalNumber(),
                proposalEntity.getApplicationDate(),
                proposalEntity.isAcceptStatement(),
                proposalEntity.getStatusProposal(),
                proposalEntity.getDescriptionRejected(),
                proposalEntity.getCreditKind()
        );
    }

    public Proposal mapToProposalFromProposalDto(final ProposalDto proposalDto){
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
                proposalDto.getApplicationDate(),
                proposalDto.isAcceptStatement(),
                proposalDto.getStatusProposal(),
                proposalDto.getDescriptionRejected(),
                proposalDto.getCreditKind()
        );
    }

    public ProposalEntity mapToProposalEntityFromProposal(final Proposal proposal) {
        return new ProposalEntity(
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
                proposal.getApplicationDate(),
                proposal.isAcceptStatement(),
                proposal.getStatusProposal(),
                proposal.getDescriptionRejected(),
                proposal.getCreditKind()
        );
    }
}
