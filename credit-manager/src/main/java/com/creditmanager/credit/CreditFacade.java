package com.creditmanager.credit;

import com.creditmanager.credit.enums.CreditKind;
import com.creditmanager.proposal.ProposalDto;
import com.creditmanager.proposal.ProposalFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditFacade {

    private final AdapterCreditEntityRepository adapterCreditEntityRepository;
    private final CreditFactory creditFactory;
    private final ProposalFacade proposalFacade;

    public boolean acceptCredit(final ProposalDto proposalDto) {
        log.info("accept credit");
        if (proposalFacade.checkingSalaryToCreditPossibilities(proposalDto)) {
            acceptProposal(proposalDto);
            return true;
        }
        return false;
    }
    public boolean checkAccountAlreadyHaveThatKindCredit(final Long accountId, final CreditKind creditKind) {
        log.info("check Account Already Have That Kind Credit");
        return adapterCreditEntityRepository.existsByAccountIdAndCreditKind(accountId, creditKind);
    }
    private void acceptProposal(final ProposalDto proposalDto) {
        log.info("accept proposal");
        Credit creditAccept = creditFactory.buildCreditFromProposalDto(proposalDto);
        adapterCreditEntityRepository.save(creditFactory.toCreditEntityFromCredit(creditAccept));
        log.info("proposal saved");
    }
}
