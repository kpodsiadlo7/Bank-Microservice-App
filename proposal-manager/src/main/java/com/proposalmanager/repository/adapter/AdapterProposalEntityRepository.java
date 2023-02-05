package com.proposalmanager.repository.adapter;

import com.proposalmanager.domain.ProposalEntity;

import java.util.Set;

public interface AdapterProposalEntityRepository {
    ProposalEntity findByProposalNumber(String proposalNumber);
    boolean existsByProposalNumber(String proposalNumber);
    ProposalEntity save(ProposalEntity proposalEntity);
    Set<ProposalEntity> findAllByUserId(long userId);
}
