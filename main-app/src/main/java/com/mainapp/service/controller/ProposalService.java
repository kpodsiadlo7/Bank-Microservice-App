package com.mainapp.service.controller;

import com.mainapp.service.data.Proposal;
import com.mainapp.service.data.User;
import com.mainapp.service.mapper.ProposalMapper;
import com.mainapp.web.dto.AccountDto;
import com.mainapp.web.dto.ProposalDto;
import com.mainapp.web.feign.FeignServiceAccountsManager;
import com.mainapp.web.feign.FeignServiceProposalManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.TreeSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProposalService {

    private final FeignServiceProposalManager feignServiceProposalManager;
    private final FeignServiceAccountsManager feignServiceAccountsManager;
    private final ProposalMapper proposalMapper;

    public String getProposal(final String proposalNumber, ModelMap modelMap) {
        log.info("get proposal");
        Proposal returningProposal;
        try {
            returningProposal = proposalMapper.mapToProposalFromProposalDto
                    (feignServiceProposalManager.getProposalByNumber(proposalNumber));
        } catch (Exception e) {
            log.warn("problem with fetching your proposal");
            modelMap.put("proposalDto", new ProposalDto());
            modelMap.put("error", "There is a problem with fetching your proposal");
            return "proposal";
        }
        modelMap.put("proposalDto", returningProposal);
        log.info("successful received proposal");
        log.info(String.valueOf(returningProposal.getStatusProposal()));
        return "proposal";
    }

    public String validateBeforePost(final User user, final Proposal proposal, final Long accountId, ModelMap modelMap, final String creditKind) {
        log.info("validate before post");
        Proposal returningProposal;
        TreeSet<AccountDto> fetchingAccounts = new TreeSet<>();
        try {
            returningProposal = proposalMapper.mapToProposalFromProposalDto(feignServiceProposalManager.validateProposalBeforePost(
                    proposalMapper.mapToProposalDtoFromProposal(proposal), accountId, creditKind));
            if (returningProposal.getCurrency().equals("error")) {
                log.warn("error " + returningProposal.getProposalNumber());
                modelMap.put("proposalDto", proposalMapper.mapToProposalDtoFromProposal(proposal));
                modelMap.put("error", returningProposal.getProposalNumber());
                try {
                    fetchingAccounts = feignServiceAccountsManager.getAllAccountsByUserId(user.getId());
                } catch (Exception e) {
                    log.warn("error with fetching account");
                    modelMap.put("error", "Error with fetching your accounts");
                }
                modelMap.put("accountsDto", fetchingAccounts);
                return "credits";
            }
        } catch (Exception e) {
            log.warn("error with connecting to proposal manager");
            modelMap.put("error", "There is a problem with connecting to proposal-manager");
            modelMap.put("accountsDto", fetchingAccounts);
            return "credits";
        }
        log.info("successful receive proposal");
        return "redirect:/dashboard/credit/" + returningProposal.getProposalNumber();
    }

    public String postProposal(final String proposalNumber, ModelMap modelMap) {
        log.warn("post proposal");

        try {
            feignServiceProposalManager.acceptProposal(proposalNumber);
        } catch (Exception e) {
            log.warn("problem with connecting to proposal manager");
            modelMap.put("error", "Problem with connecting to proposal manager");
            modelMap.put("accountsDto", List.of(new AccountDto()));
            modelMap.put("proposalDto", new ProposalDto());
            return "credits";
        }
        log.info("successful accept proposal");
        return "redirect:/dashboard/credit/" + proposalNumber;
    }
}
