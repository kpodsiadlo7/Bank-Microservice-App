package com.proposalmanager.repository;

import com.proposalmanager.domain.ProposalEntity;
import com.proposalmanager.repository.adapter.AdapterProposalEntityRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ProposalEntityRepository extends AdapterProposalEntityRepository, JpaRepository<ProposalEntity,Long> {
}
