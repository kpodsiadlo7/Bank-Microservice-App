package com.proposalmanager.proposal;

import com.proposalmanager.domain.ProposalEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
                proposalEntity.getDateFrom(),
                proposalEntity.getDateTo(),
                proposalEntity.isAcceptStatement(),
                proposalEntity.getStatusProposal(),
                proposalEntity.getDescriptionRejected(),
                proposalEntity.getCreditKind()
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
                proposal.getDateFrom(),
                proposal.getDateTo(),
                proposal.isAcceptStatement(),
                proposal.getStatusProposal(),
                proposal.getDescriptionRejected(),
                proposal.getCreditKind()
        );
    }

    public ProposalEntity updateProposalEntity(final Proposal proposal) {
        return new ProposalEntity(
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

    public Set<ProposalDto> mapToProposalDtoSetFromProposalSet(final Set<Proposal> proposals) {
        Set<ProposalDto> proposalDtos = new HashSet<>();
        for (Proposal p: proposals){
            proposalDtos.add(mapToProposalDtoFromProposal(p));
        }
        return proposalDtos;
    }

    public Set<Proposal> mapToProposalSetFromProposalEntitySet(final Set<ProposalEntity> proposalEntities) {
        Set<Proposal> proposals = new HashSet<>();
        for (ProposalEntity p: proposalEntities){
            proposals.add(mapToProposalFromProposalEntity(p));
        }
        return proposals;
    }
}
