package com.mainapp.web.controller;

import com.mainapp.service.controller.CreditService;
import com.mainapp.service.controller.ProposalService;
import com.mainapp.service.data.User;
import com.mainapp.service.mapper.ProposalMapper;
import com.mainapp.web.dto.ProposalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/credit")
public class CreditController {

    private final CreditService creditService;
    private final ProposalService proposalService;
    private final ProposalMapper proposalMapper;

    @GetMapping
    public String getCredit(ModelMap modelMap) {
        return creditService.getCredit(modelMap);
    }

    @GetMapping("{proposalNumber}")
    public String getProposal(@PathVariable String proposalNumber, ModelMap modelMap) {
        return proposalService.getProposal(proposalNumber, modelMap);
    }

    @PostMapping
    public String postProposal(@AuthenticationPrincipal User user, @ModelAttribute ProposalDto proposalDto,
                               @RequestParam(name = "accountId") Long accountId, ModelMap modelMap) {
        return proposalService.validateBeforePostAndPost(user,
                proposalMapper.mapToProposalFromProposalDto(proposalDto),accountId,modelMap);
    }
}
