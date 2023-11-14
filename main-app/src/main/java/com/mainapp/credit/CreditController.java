package com.mainapp.credit;

import com.mainapp.proposal.dto.DataForProposalDto;
import com.mainapp.proposal.ProposalFacade;
import com.mainapp.user.User;
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
    private final ProposalFacade proposalFacade;

    @GetMapping
    String getCredit(@AuthenticationPrincipal User user,
                            ModelMap modelMap) {
        return creditFacade.getCredits(user, modelMap);
    }

    @GetMapping("{proposalNumber}")
    String getProposal(@PathVariable String proposalNumber, ModelMap modelMap) {
        return proposalFacade.getProposal(proposalNumber, modelMap);
    }

    @PostMapping
    String prepareProposal(@AuthenticationPrincipal User user,
                                  @ModelAttribute final DataForProposalDto dataForProposalDto,
                                  @RequestParam(name = "accountId") Long accountId, ModelMap modelMap,
                                  @RequestParam(name = "creditKind") String creditKind,
                                  @RequestParam(name = "promotion") String promotion) {
        log.info("prepareProposal");
        return proposalFacade.validateBeforePost(user, dataForProposalDto, accountId, modelMap, creditKind, promotion);
    }

    @PostMapping("{proposalNumber}")
    String postProposal(@RequestParam(name = "accountId") Long accountId,
                               @RequestParam(name = "monthlyFee") double monthlyFee,
                               @PathVariable String proposalNumber,
                               ModelMap modelMap) {
        log.info("postProposal");
        return proposalFacade.postProposal(accountId,monthlyFee,proposalNumber, modelMap);
    }
}
