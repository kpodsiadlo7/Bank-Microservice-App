package com.proposalmanager.proposal;

import com.proposalmanager.domain.ProposalEntity;
import com.proposalmanager.domain.enums.StatusProposal;

import java.util.Set;

public interface AdapterProposalEntityRepository {
    ProposalEntity findByProposalNumber(String proposalNumber);

    boolean existsByProposalNumber(String proposalNumber);

    ProposalEntity save(ProposalEntity proposalEntity);

    Set<ProposalEntity> findAllByUserId(long userId);
    boolean existsByAccountIdAndStatusProposal(long accountId, StatusProposal statusProposal);
}
