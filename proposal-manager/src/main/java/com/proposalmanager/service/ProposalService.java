package com.proposalmanager.service;

import com.proposalmanager.repository.adapter.AdapterProposalEntityRepository;
import com.proposalmanager.service.data.Proposal;
import com.proposalmanager.service.mapper.ProposalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProposalService {

    private final AdapterProposalEntityRepository adapterProposalEntityRepository;
    private final ProposalMapper proposalMapper;

    public Proposal getProposalByNumber(final String proposalNumber) {
        if (!adapterProposalEntityRepository.existsByProposalNumber(proposalNumber)){
            Proposal error = new Proposal();
            error.setCurrency("error");
            error.setProposalNumber("Proposal with given number doesn't exist!");
            return error;
        }
        return proposalMapper.mapToProposalFromProposalEntity
                        (adapterProposalEntityRepository.findByProposalNumber(proposalNumber));
    }
}
