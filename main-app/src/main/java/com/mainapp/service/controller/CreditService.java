package com.mainapp.service.controller;

import com.mainapp.web.dto.ProposalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
@RequiredArgsConstructor
public class CreditService {

    public String getCredit(ModelMap modelMap) {
        modelMap.put("proposalDto", new ProposalDto());
        return "credit";
    }
}
