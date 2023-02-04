package com.proposalmanager.repository.adapter;

import com.proposalmanager.domain.ProposalEntity;

public interface AdapterProposalEntityRepository {
    ProposalEntity findByProposalNumber(String proposalNumber);
    boolean existsByProposalNumber(String proposalNumber);
    ProposalEntity save(ProposalEntity proposalEntity);
}
