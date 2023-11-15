package com.proposalmanager.proposal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ProposalEntityRepository extends AdapterProposalEntityRepository, JpaRepository<ProposalEntity,Long> {
}
