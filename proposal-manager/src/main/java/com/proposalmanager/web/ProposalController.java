package com.proposalmanager.web;

import com.proposalmanager.service.ProposalService;
import com.proposalmanager.service.mapper.ProposalMapper;
import com.proposalmanager.web.dto.ProposalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProposalController {

    private final ProposalMapper proposalMapper;
    private final ProposalService proposalService;

    @GetMapping
    public ResponseEntity<ProposalDto> getProposalByNumber(@RequestParam String proposalNumber){
        return ResponseEntity.ok(proposalMapper.mapToProposalDtoFromProposal
                (proposalService.getProposalByNumber(proposalNumber)));
    }
}
