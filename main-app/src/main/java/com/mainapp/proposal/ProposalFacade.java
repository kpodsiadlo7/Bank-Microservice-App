package com.mainapp.proposal;

import com.mainapp.account.AccountFacade;
import com.mainapp.proposal.dto.DataForProposalDto;
import com.mainapp.proposal.dto.ProposalDto;
import com.mainapp.user.User;
import com.mainapp.account.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProposalFacade {

    private final FeignServiceProposalManager feignServiceProposalManager;
    private final ProposalFactory proposalFactory;
    private final AccountFacade accountFacade;

    public String getProposal(final String proposalNumber, ModelMap modelMap) {
        log.info("get proposal");
        final ProposalDto returningProposalDto;
        try {
            returningProposalDto = feignServiceProposalManager.getProposalByNumber(proposalNumber);
        } catch (Exception e) {
            log.warn("problem with fetching your proposal");
            modelMap.put("proposalDto", ProposalDto.builder().build());
            modelMap.put("error", "There is a problem with fetching your proposal");
            return "proposal";
        }
        modelMap.put("proposalDto", returningProposalDto);
        log.info("account id form model proposal " + returningProposalDto.getAccountId());
        log.info("successful received proposal");
        log.info(String.valueOf(returningProposalDto.getStatusProposal()));
        return "proposal";
    }

    public String validateBeforePost(final User user,
                                     final DataForProposalDto dataForProposalDto,
                                     final Long accountId, ModelMap modelMap,
                                     final String creditKind,
                                     final String promotion) {
        log.info("validate before post");
        ProposalDto returningProposalDto;
        TreeSet<AccountDto> fetchingAccounts = new TreeSet<>();
        try {
            returningProposalDto = feignServiceProposalManager.
                    validateProposalBeforePost
                            (proposalFactory.buildProposalDtoFromDataForProposalDto(dataForProposalDto),
                                    accountId, creditKind, promotion);

            if (returningProposalDto.getCurrency().equals("error")) {
                log.warn("error " + returningProposalDto.getProposalNumber());
                modelMap.put("proposalDto", returningProposalDto);
                modelMap.put("error", returningProposalDto.getProposalNumber());
                try {
                    fetchingAccounts = accountFacade.getAllAccountsByUserId(user.getId());
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
        return "redirect:/dashboard/credit/" + returningProposalDto.getProposalNumber();
    }

    public String postProposal(final Long accountId,
                               final double monthlyFee,
                               final String proposalNumber, ModelMap modelMap) {
        log.warn("post proposal");

        try {
            if(feignServiceProposalManager.acceptProposal(proposalNumber)){
            accountFacade.setCommitmentsToAccount(accountId, monthlyFee);
            }
        } catch (Exception e) {
            log.warn("problem with connecting to proposal manager");
            modelMap.put("error", "Problem with connecting to proposal manager");
            modelMap.put("accountsDto", List.of(AccountDto.builder().build()));
            modelMap.put("proposalDto", ProposalDto.builder().build());
            return "credits";
        }
        log.info("successful accept proposal");
        return "redirect:/dashboard/credit/" + proposalNumber;
    }

    public Set<ProposalDto> getAllProposalsByUserId(Long userId) {
        return feignServiceProposalManager.getAllProposalsByUserId(userId);
    }
}
