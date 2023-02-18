package com.proposalmanager.repository;

import com.proposalmanager.domain.ProposalEntity;
import com.proposalmanager.domain.enums.StatusProposal;
import com.proposalmanager.repository.adapter.AdapterProposalEntityRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalEntityRepository extends AdapterProposalEntityRepository, JpaRepository<ProposalEntity,Long> {
}
