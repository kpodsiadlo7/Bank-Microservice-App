package com.mainapp.web.feign;

import com.mainapp.service.data.Proposal;
import com.mainapp.service.data.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "proposal-manager", url = "http://proposal-manager:8500")
public interface FeignServiceProposalManager {
    @GetMapping
    Proposal getProposalByNumber(@RequestParam String proposalNumber);

    @PostMapping("/validate-proposal")
    Proposal validateProposalBeforePost(@RequestParam User user,
                                        @RequestParam Proposal proposal,
                                        @RequestParam Long accountId);
}
