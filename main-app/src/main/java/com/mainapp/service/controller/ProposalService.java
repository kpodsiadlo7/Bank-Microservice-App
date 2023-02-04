package com.mainapp.service.controller;

import com.mainapp.service.data.Proposal;
import com.mainapp.service.data.User;
import com.mainapp.service.mapper.ProposalMapper;
import com.mainapp.web.dto.AccountDto;
import com.mainapp.web.dto.ProposalDto;
import com.mainapp.web.feign.FeignServiceAccountsManager;
import com.mainapp.web.feign.FeignServiceProposalManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class ProposalService {

    private final FeignServiceProposalManager feignServiceProposalManager;
    private final FeignServiceAccountsManager feignServiceAccountsManager;
    private final ProposalMapper proposalMapper;

    public String getProposal(final String proposalNumber, ModelMap modelMap) {
        Proposal returningProposal;
        try {
            returningProposal = proposalMapper.mapToProposalFromProposalDto(feignServiceProposalManager.getProposalByNumber(proposalNumber));
        } catch (Exception e) {
            modelMap.put("proposalDto", new ProposalDto());
            modelMap.put("error", "There is a problem with fetching your proposal");
            return "credit";
        }
        modelMap.put("proposalDto", proposalMapper.mapToProposalDtoFromProposal(returningProposal));
        return "credit";
    }

    public String validateBeforePostAndPost(final User user, final Proposal proposal, final Long accountId, ModelMap modelMap, final String creditKind) {
        Proposal returningProposal;
        TreeSet<AccountDto> fetchingAccounts = new TreeSet<>();
        try {
            returningProposal = proposalMapper.mapToProposalFromProposalDto(feignServiceProposalManager.validateProposalBeforePost(
                    proposalMapper.mapToProposalDtoFromProposal(proposal), accountId, creditKind));
            if (returningProposal.getCurrency().equals("error")) {
                modelMap.put("proposalDto", proposalMapper.mapToProposalDtoFromProposal(proposal));
                modelMap.put("error", returningProposal.getProposalNumber());
                try {
                    fetchingAccounts = feignServiceAccountsManager.getAllAccountsByUserId(user.getId());
                } catch (Exception e){
                    modelMap.put("error","Error with fetching your accounts");
                }
                modelMap.put("accountsDto", fetchingAccounts);
                return "credit";
            }
        } catch (Exception e) {
            modelMap.put("error", "There is a problem with connecting to proposal-manager");
            return "credit";
        }
        return "redirect:/dashboard/credit/" + returningProposal.getProposalNumber();
    }
}
