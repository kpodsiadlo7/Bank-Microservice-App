package com.mainapp.service.mapper;

import com.mainapp.service.data.Proposal;
import com.mainapp.web.dto.ProposalDto;
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
                proposal.getApplicationNumber(),
                proposal.getApplicationDate(),
                proposal.getStatusProposal()
        );
    }
}
