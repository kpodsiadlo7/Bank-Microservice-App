package com.mainapp.service.controller;

import com.mainapp.service.data.Proposal;
import com.mainapp.service.data.User;
import com.mainapp.service.mapper.ProposalMapper;
import com.mainapp.web.dto.ProposalDto;
import com.mainapp.web.feign.FeignServiceProposalManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
@RequiredArgsConstructor
public class ProposalService {

    private final FeignServiceProposalManager feignServiceProposalManager;
    private final ProposalMapper proposalMapper;
    public String getProposal(final String proposalNumber, ModelMap modelMap) {
        Proposal returningProposal;
        try {
            returningProposal = proposalMapper.mapToProposalFromProposalDto(feignServiceProposalManager.getProposalByNumber(proposalNumber));
        } catch (Exception e){
            modelMap.put("proposalDto", new ProposalDto());
            modelMap.put("error","There is a problem with fetching your proposal");
            return "credit";
        }
        modelMap.put("proposalDto",proposalMapper.mapToProposalDtoFromProposal(returningProposal));
        return "credit";
    }

    public String validateBeforePostAndPost(final User user, final Proposal proposal, final Long accountId, ModelMap modelMap) {
        Proposal returningProposal;
        try {
            returningProposal = proposalMapper.mapToProposalFromProposalDto(feignServiceProposalManager.validateProposalBeforePost(user,proposal,accountId));
            if (returningProposal.getCurrency().equals("error")){
                modelMap.put("proposalDto", proposalMapper.mapToProposalDtoFromProposal(proposal));
                modelMap.put("error",returningProposal.getProposalNumber());
                return "credit";
            }
        } catch (Exception e){
            modelMap.put("error","There is a problem with connecting to proposal-manager");
            return "credit";
        }
        return "redirect:/dashboard/credit/"+returningProposal.getProposalNumber();
    }
}
