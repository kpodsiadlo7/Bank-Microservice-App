package com.proposalmanager.web;

import com.proposalmanager.service.ProposalService;
import com.proposalmanager.service.mapper.ProposalMapper;
import com.proposalmanager.web.dto.ProposalDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProposalController {

    private final ProposalMapper proposalMapper;
    private final ProposalService proposalService;

    @GetMapping
    public ResponseEntity<ProposalDto> getProposalByNumber(@RequestParam String proposalNumber) {
        return ResponseEntity.ok(proposalMapper.mapToProposalDtoFromProposal
                (proposalService.getProposalByNumber(proposalNumber)));
    }

    @GetMapping("/proposals")
    public ResponseEntity<Set<ProposalDto>> getAllProposalsByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(proposalMapper.mapToProposalDtoSetFromProposalSet
                (proposalService.getAllProposalsByUserId(userId)));
    }

    @PostMapping("/validate-proposal")
    public ResponseEntity<ProposalDto> validateProposalBeforePost(@RequestBody ProposalDto proposalDto,
                                                                  @RequestParam Long accountId,
                                                                  @RequestParam String creditKind,
                                                                  @RequestParam String promotion) {
        return ResponseEntity.ok(proposalMapper.mapToProposalDtoFromProposal
                (proposalService.validateProposalBeforePost(proposalMapper.mapToProposalFromProposalDto(proposalDto), accountId, creditKind, promotion)));
    }

    @PostMapping("/accept-proposal")
    public ResponseEntity<Void> postProposal(@RequestParam String proposalNumber) {
        proposalService.postProposal(proposalNumber);
        return ResponseEntity.ok().build();
    }
}
