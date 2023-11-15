package com.proposalmanager.proposal.dto;

import com.proposalmanager.proposal.Proposal;
import com.proposalmanager.transfer.TransferDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProposalToTransfer {
    public TransferDto mapToTransferFromProposal(final Proposal proposal) {
        return new TransferDto(
                proposal.getUserId(),
                proposal.getAccountId(),
                BigDecimal.valueOf(proposal.getAmountOfCredit()),
                "credit"
        );
    }
}
