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
                proposal.getStatusProposal()
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
                proposalEntity.getStatusProposal()
        );
    }
}
