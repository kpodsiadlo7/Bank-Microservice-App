package com.proposalmanager.adapter;

import com.proposalmanager.proposal.Proposal;
import com.proposalmanager.transfer.Transfer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProposalToTransfer {
    public Transfer mapToTransferFromProposal(final Proposal proposal) {
        return new Transfer(
                proposal.getUserId(),
                proposal.getAccountId(),
                BigDecimal.valueOf(proposal.getAmountOfCredit()),
                "credit"
        );
    }
}
