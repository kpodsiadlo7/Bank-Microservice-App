package com.proposalmanager.credit;

import com.proposalmanager.credit.enums.CreditKind;
import com.proposalmanager.proposal.Proposal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditFacade {
    private final FeignServiceCreditManager feignServiceCreditManager;

    public boolean checkAccountAlreadyHaveThatKindCredit(final Long accountId, final CreditKind creditKind) {
        log.info("check Account Already Have That Kind Credit");
        return feignServiceCreditManager.checkAccountAlreadyHaveThatKindCredit(accountId, creditKind);
    }

    public CreditKind whatKindOfCredit(final String creditKind) {
        log.info("what kind of credit");
        switch (creditKind) {
            case "MORTGAGE" -> {
                return CreditKind.MORTGAGE;
            }
            case "DEBIT" -> {
                return CreditKind.DEBIT;
            }
        }
        return CreditKind.CASH;
    }

    public boolean acceptCredit(Proposal proposal) {
        return feignServiceCreditManager.acceptCredit(proposal);
    }
}
