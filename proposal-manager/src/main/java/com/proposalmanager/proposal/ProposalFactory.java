package com.proposalmanager.proposal;

import com.proposalmanager.proposal.dto.ProposalDto;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
class ProposalFactory {

    ProposalDto buildProposalDtoFromProposal(final Proposal proposal) {
        return ProposalDto.builder()
                .withId(proposal.getId())
                .withUserId(proposal.getUserId())
                .withAccountId(proposal.getAccountId())
                .withAmountOfCredit(proposal.getAmountOfCredit())
                .withMonth(proposal.getMonth())
                .withSalary(proposal.getSalary())
                .withInterest(proposal.getInterest())
                .withCommission(proposal.getCommission())
                .withMonthlyFee(proposal.getMonthlyFee())
                .withCurrency(proposal.getCurrency())
                .withCurrencySymbol(proposal.getCurrencySymbol())
                .withUsername(proposal.getUsername())
                .withPurpose(proposal.getPurpose())
                .withProposalNumber(proposal.getProposalNumber())
                .withDateFrom(proposal.getDateFrom())
                .withDateTo(proposal.getDateTo())
                .withAcceptStatement(proposal.isAcceptStatement())
                .withStatusProposal(proposal.getStatusProposal())
                .withDescriptionRejected(proposal.getDescriptionRejected())
                .withCreditKind(proposal.getCreditKind()).build();
    }

    Proposal mapToProposalFromProposalEntity(final ProposalEntity proposalEntity) {
        return Proposal.builder()
                .withId(proposalEntity.getId())
                .withUserId(proposalEntity.getUserId())
                .withAccountId(proposalEntity.getAccountId())
                .withAmountOfCredit(proposalEntity.getAmountOfCredit())
                .withMonth(proposalEntity.getMonth())
                .withSalary(proposalEntity.getSalary())
                .withInterest(proposalEntity.getInterest())
                .withCommission(proposalEntity.getCommission())
                .withMonthlyFee(proposalEntity.getMonthlyFee())
                .withCurrency(proposalEntity.getCurrency())
                .withCurrencySymbol(proposalEntity.getCurrencySymbol())
                .withUsername(proposalEntity.getUsername())
                .withPurpose(proposalEntity.getPurpose())
                .withProposalNumber(proposalEntity.getProposalNumber())
                .withDateFrom(proposalEntity.getDateFrom())
                .withDateTo(proposalEntity.getDateTo())
                .withAcceptStatement(proposalEntity.isAcceptStatement())
                .withStatusProposal(proposalEntity.getStatusProposal())
                .withDescriptionRejected(proposalEntity.getDescriptionRejected())
                .withCreditKind(proposalEntity.getCreditKind()).build();
    }

    Proposal mapToProposalFromProposalDto(final ProposalDto proposalDto) {
        return Proposal.builder()
                .withId(proposalDto.getId())
                .withUserId(proposalDto.getUserId())
                .withAccountId(proposalDto.getAccountId())
                .withAmountOfCredit(proposalDto.getAmountOfCredit())
                .withMonth(proposalDto.getMonth())
                .withSalary(proposalDto.getSalary())
                .withInterest(proposalDto.getInterest())
                .withCommission(proposalDto.getCommission())
                .withMonthlyFee(proposalDto.getMonthlyFee())
                .withCurrency(proposalDto.getCurrency())
                .withCurrencySymbol(proposalDto.getCurrencySymbol())
                .withUsername(proposalDto.getUsername())
                .withPurpose(proposalDto.getPurpose())
                .withProposalNumber(proposalDto.getProposalNumber())
                .withDateFrom(proposalDto.getDateFrom())
                .withDateTo(proposalDto.getDateTo())
                .withAcceptStatement(proposalDto.isAcceptStatement())
                .withStatusProposal(proposalDto.getStatusProposal())
                .withDescriptionRejected(proposalDto.getDescriptionRejected())
                .withCreditKind(proposalDto.getCreditKind()).build();
    }

    ProposalEntity mapToProposalEntityFromProposal(final Proposal proposal) {
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

    ProposalEntity updateProposalEntity(final Proposal proposal) {
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

    Set<ProposalDto> mapToProposalDtoSetFromProposalSet(final Set<Proposal> proposals) {
        Set<ProposalDto> proposalDtos = new HashSet<>();
        for (Proposal p: proposals){
            proposalDtos.add(buildProposalDtoFromProposal(p));
        }
        return proposalDtos;
    }

    Set<Proposal> mapToProposalSetFromProposalEntitySet(final Set<ProposalEntity> proposalEntities) {
        Set<Proposal> proposals = new HashSet<>();
        for (ProposalEntity p: proposalEntities){
            proposals.add(mapToProposalFromProposalEntity(p));
        }
        return proposals;
    }
}
