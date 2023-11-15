package com.proposalmanager.proposal;

import com.proposalmanager.proposal.dto.ProposalDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
class ProposalController {

    private final ProposalFactory proposalFactory;
    private final ProposalFacade proposalFacade;

    @GetMapping
    ResponseEntity<ProposalDto> getProposalByNumber(@RequestParam String proposalNumber) {
        return ResponseEntity.ok(proposalFactory.buildProposalDtoFromProposal
                (proposalFacade.getProposalByNumber(proposalNumber)));
    }

    @GetMapping("/proposals")
    ResponseEntity<Set<ProposalDto>> getAllProposalsByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(proposalFactory.mapToProposalDtoSetFromProposalSet
                (proposalFacade.getAllProposalsByUserId(userId)));
    }

    @PostMapping("/validate-proposal")
    ResponseEntity<ProposalDto> validateProposalBeforePost(@RequestBody ProposalDto proposalDto,
                                                                  @RequestParam Long accountId,
                                                                  @RequestParam String creditKind,
                                                                  @RequestParam String promotion) {
        return ResponseEntity.ok(proposalFactory.buildProposalDtoFromProposal(
                (proposalFacade.validateProposalBeforePost(proposalFactory.mapToProposalFromProposalDto(proposalDto), accountId, creditKind, promotion))));
    }

    @PostMapping("/accept-proposal")
    ResponseEntity<Boolean> postProposal(@RequestParam String proposalNumber) {
        return ResponseEntity.ok(proposalFacade.postProposal(proposalNumber));
    }
}
