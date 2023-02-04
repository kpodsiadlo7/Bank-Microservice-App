package com.proposalmanager.service;

import com.proposalmanager.domain.enums.CreditKind;
import com.proposalmanager.service.data.Credit;
import com.proposalmanager.web.feign.FeignServiceCreditManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditService {
    private final FeignServiceCreditManager feignServiceCreditManager;

    public Credit checkAccountAlreadyHaveThatKindCredit(final Long accountId, final CreditKind creditKind, Credit fetchingCredit) {
        try {
            boolean response = feignServiceCreditManager.checkAccountAlreadyHaveThatKindCredit(accountId, creditKind);
            if (response) {
                fetchingCredit.setUserId(-1L);
                fetchingCredit.setProposalNumber("You already have that kind of credit on this account");
                return fetchingCredit;
            }
        } catch (Exception e) {
            fetchingCredit.setUserId(-1L);
            fetchingCredit.setProposalNumber("Problem with connecting to credit-manager");
            return fetchingCredit;
        }
        // this user id is completely not needed for anything, I set it only for validation
        fetchingCredit.setUserId(7L);
        return fetchingCredit;
    }
}
