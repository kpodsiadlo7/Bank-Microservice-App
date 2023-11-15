package com.proposalmanager.proposal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
class ProposalController {

    private final ProposalMapper proposalMapper;
    private final ProposalService proposalService;

    @GetMapping
    ResponseEntity<ProposalDto> getProposalByNumber(@RequestParam String proposalNumber) {
        return ResponseEntity.ok(proposalMapper.mapToProposalDtoFromProposal
                (proposalService.getProposalByNumber(proposalNumber)));
    }

    @GetMapping("/proposals")
    ResponseEntity<Set<ProposalDto>> getAllProposalsByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(proposalMapper.mapToProposalDtoSetFromProposalSet
                (proposalService.getAllProposalsByUserId(userId)));
    }

    @PostMapping("/validate-proposal")
    ResponseEntity<ProposalDto> validateProposalBeforePost(@RequestBody ProposalDto proposalDto,
                                                                  @RequestParam Long accountId,
                                                                  @RequestParam String creditKind,
                                                                  @RequestParam String promotion) {
        return ResponseEntity.ok(proposalMapper.mapToProposalDtoFromProposal
                (proposalService.validateProposalBeforePost(proposalMapper.mapToProposalFromProposalDto(proposalDto), accountId, creditKind, promotion)));
    }

    @PostMapping("/accept-proposal")
    ResponseEntity<Boolean> postProposal(@RequestParam String proposalNumber) {
        return ResponseEntity.ok(proposalService.postProposal(proposalNumber));
    }
}
