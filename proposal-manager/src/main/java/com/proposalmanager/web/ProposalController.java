package com.proposalmanager.web;

import com.proposalmanager.service.ProposalService;
import com.proposalmanager.service.mapper.ProposalMapper;
import com.proposalmanager.web.dto.ProposalDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/validate-proposal")
    public ResponseEntity<ProposalDto> validateProposalBeforePost(@RequestBody ProposalDto proposalDto,
                                                                  @RequestParam Long accountId,
                                                                  @RequestParam String creditKind) {
        log.info("controller");
        log.info("should be 'bo tak'" +proposalDto.getPurpose());
        log.info("should be 1 "+accountId);
        return ResponseEntity.ok(proposalMapper.mapToProposalDtoFromProposal
                (proposalService.validateProposalBeforePost(proposalMapper.mapToProposalFromProposalDto(proposalDto), accountId, creditKind)));
    }
}
