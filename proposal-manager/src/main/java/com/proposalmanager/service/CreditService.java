package com.proposalmanager.service;

import com.proposalmanager.domain.enums.CreditKind;
import com.proposalmanager.service.data.Credit;
import com.proposalmanager.web.feign.FeignServiceCreditManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class CreditService {
    private final FeignServiceCreditManager feignServiceCreditManager;

    public Credit checkAccountAlreadyHaveThatKindCredit(final Long accountId, final CreditKind creditKind, Credit fetchingCredit) {
        log.info("check Account Already Have That Kind Credit");
        try {
            boolean response = feignServiceCreditManager.checkAccountAlreadyHaveThatKindCredit(accountId, creditKind);
            if (response) {
                log.warn("this account already have this kind of credit");
                fetchingCredit.setUserId(-1L);
                fetchingCredit.setProposalNumber("You already have that kind of credit on this account");
                return fetchingCredit;
            }
        } catch (Exception e) {
            log.warn("problem with connecting to credit manager");
            fetchingCredit.setUserId(-1L);
            fetchingCredit.setProposalNumber("Problem with connecting to credit-manager");
            return fetchingCredit;
        }
        // this user id is completely not needed for anything, I set it only for validation
        fetchingCredit.setUserId(7L);
        log.info("this account not have that kind credit");
        return fetchingCredit;
    }
}
