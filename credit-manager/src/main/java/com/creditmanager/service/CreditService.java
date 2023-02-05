package com.creditmanager.service;

import com.creditmanager.domain.enums.CreditKind;
import com.creditmanager.repository.adapter.AdapterCreditEntityRepository;
import com.creditmanager.service.data.Credit;
import com.creditmanager.service.data.Proposal;
import com.creditmanager.service.mapper.CreditMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final AdapterCreditEntityRepository adapterCreditEntityRepository;
    private final CreditMapper creditMapper;

    public boolean acceptCredit(final Proposal proposal) {
        if (checkingSalaryToCreditPossibilities(proposal)) {
            acceptProposal(proposal);
            return true;
        }
        return false;
    }

    public boolean checkAccountAlreadyHaveThatKindCredit(final Long accountId, final CreditKind creditKind) {
        return adapterCreditEntityRepository.existsByAccountIdAndCreditKind(accountId, creditKind);
    }

    public void acceptProposal(final Proposal proposal) {
        Credit creditAccept = new Credit();
        creditAccept.setUserId(proposal.getUserId());
        creditAccept.setAccountId(proposal.getAccountId());
        creditAccept.setProposalNumber(proposal.getProposalNumber());
        creditAccept.setCreditKind(proposal.getCreditKind());

        adapterCreditEntityRepository.save(creditMapper.mapToCreditEntityFromCredit(creditAccept));
    }

    private boolean checkingSalaryToCreditPossibilities(final Proposal proposal) {
        if (proposal.getAmountOfCredit() * 0.2 > proposal.getSalary()) {
            int random = (int) (Math.random() * 7);
            return random >= 4;
        }
        return true;
    }

}
