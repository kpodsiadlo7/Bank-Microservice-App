package com.mainapp.credit;

import com.mainapp.proposal.ProposalService;
import com.mainapp.user.User;
import com.mainapp.proposal.ProposalMapper;
import com.mainapp.proposal.ProposalDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/credit")
class CreditController {

    private final CreditFacade creditFacade;
    private final ProposalService proposalService;
    private final ProposalMapper proposalMapper;

    @GetMapping
    String getCredit(@AuthenticationPrincipal User user,
                            ModelMap modelMap) {
        return creditFacade.getCredits(user, modelMap);
    }

    @GetMapping("{proposalNumber}")
    String getProposal(@PathVariable String proposalNumber, ModelMap modelMap) {
        return proposalService.getProposal(proposalNumber, modelMap);
    }

    @PostMapping
    String prepareProposal(@AuthenticationPrincipal User user,
                                  @ModelAttribute ProposalDto proposalDto,
                                  @RequestParam(name = "accountId") Long accountId, ModelMap modelMap,
                                  @RequestParam(name = "creditKind") String creditKind,
                                  @RequestParam(name = "promotion") String promotion) {
        log.info("should be 3 " + proposalDto.getMonth());
        return proposalService.validateBeforePost(user,
                proposalMapper.mapToProposalFromProposalDto(proposalDto), accountId, modelMap, creditKind, promotion);
    }

    @PostMapping("{proposalNumber}")
    String postProposal(@RequestParam(name = "accountId") Long accountId,
                               @RequestParam(name = "monthlyFee") double monthlyFee,
                               @PathVariable String proposalNumber,
                               ModelMap modelMap) {
        return proposalService.postProposal(accountId,monthlyFee,proposalNumber, modelMap);
    }
}
