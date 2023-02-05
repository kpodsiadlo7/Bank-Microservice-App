package com.creditmanager.service.mapper;

import com.creditmanager.domain.CreditEntity;
import com.creditmanager.service.data.Credit;
import org.springframework.stereotype.Service;

@Service
public class CreditMapper {
    public CreditEntity mapToCreditEntityFromCredit(final Credit credit) {
        return new CreditEntity(
                credit.getUserId(),
                credit.getAccountId(),
                credit.getProposalNumber(),
                credit.getCreditKind()
        );
    }
}
