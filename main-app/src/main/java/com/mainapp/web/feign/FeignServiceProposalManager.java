package com.mainapp.web.feign;

import com.mainapp.web.dto.ProposalDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(value = "proposal-manager", url = "http://proposal-manager:8500")
public interface FeignServiceProposalManager {
    @GetMapping
    ProposalDto getProposalByNumber(@RequestParam String proposalNumber);

    @GetMapping("/proposals")
    Set<ProposalDto> getAllProposalsByUserId(@RequestParam Long userId);

    @PostMapping("/validate-proposal")
    ProposalDto validateProposalBeforePost(@RequestBody ProposalDto proposalDto,
                                           @RequestParam Long accountId,
                                           @RequestParam String creditKind);

    @PostMapping("/accept-proposal")
    void acceptProposal(@RequestParam String proposalNumber);
}
