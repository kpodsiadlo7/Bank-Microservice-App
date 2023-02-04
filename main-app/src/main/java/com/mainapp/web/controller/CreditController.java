package com.mainapp.web.controller;

import com.mainapp.service.controller.CreditService;
import com.mainapp.service.controller.ProposalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/credit")
public class CreditController {

    private final CreditService creditService;
    private final ProposalService proposalService;

    @GetMapping
    public String getCredit(ModelMap modelMap){
        return creditService.getCredit(modelMap);
    }

    @GetMapping("{proposalNumber}")
    public String getProposal(@PathVariable String proposalNumber, ModelMap modelMap){
        return proposalService.getProposal(proposalNumber,modelMap);
    }
}
