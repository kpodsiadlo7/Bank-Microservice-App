package com.proposalmanager.service;

import com.proposalmanager.domain.enums.CreditKind;
import com.proposalmanager.domain.enums.StatusProposal;
import com.proposalmanager.repository.adapter.AdapterProposalEntityRepository;
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
    private final AdapterProposalEntityRepository adapterProposalEntityRepository;

    public Credit checkAccountAlreadyHaveThatKindCredit(final Long accountId, final CreditKind creditKind, Credit fetchingCredit) {
        log.info("check Account Already Have That Kind Credit");
        boolean response;
        try {
            response = feignServiceCreditManager.checkAccountAlreadyHaveThatKindCredit(accountId, creditKind);
        } catch (Exception e) {
            log.warn("problem with connecting to credit manager");
            fetchingCredit.setUserId(-1L);
            fetchingCredit.setProposalNumber("Problem with connecting to credit-manager");
            return fetchingCredit;
        }
        if (adapterProposalEntityRepository.existsByAccountIdAndStatusProposal(accountId, StatusProposal.OPEN)) {
            log.warn("You already have open proposal for that kind of credit");
            fetchingCredit.setUserId(-1L);
            fetchingCredit.setProposalNumber("You already have open proposal");
            return fetchingCredit;
        }
        if (response && !adapterProposalEntityRepository.existsByAccountIdAndStatusProposal(accountId,StatusProposal.REJECTED) ||
                adapterProposalEntityRepository.existsByAccountIdAndStatusProposal(accountId,StatusProposal.ACCEPT)) {
            log.warn("You already have that kind of credit on this account");
            fetchingCredit.setUserId(-1L);
            fetchingCredit.setProposalNumber("You already have that kind of credit on this account");
            return fetchingCredit;
        }
        // this user id is completely not needed for anything, I set it only for validation
        fetchingCredit.setUserId(7L);
        log.info("this account not have that kind credit");
        return fetchingCredit;
    }
}
