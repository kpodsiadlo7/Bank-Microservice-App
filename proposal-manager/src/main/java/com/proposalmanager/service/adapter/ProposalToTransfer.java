package com.proposalmanager.service.adapter;

import com.proposalmanager.service.data.Proposal;
import com.proposalmanager.service.data.Transfer;
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
