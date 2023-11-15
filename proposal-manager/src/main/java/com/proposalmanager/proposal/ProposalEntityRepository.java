package com.proposalmanager.proposal;

import com.proposalmanager.domain.ProposalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ProposalEntityRepository extends AdapterProposalEntityRepository, JpaRepository<ProposalEntity,Long> {
}
