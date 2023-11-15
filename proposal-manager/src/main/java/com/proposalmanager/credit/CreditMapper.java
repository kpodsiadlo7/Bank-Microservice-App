package com.proposalmanager.credit;

import org.springframework.stereotype.Service;

@Service
public class CreditMapper {
    public Credit mapToCreditFromCreditDto(final CreditDto creditDto) {
        return new Credit(
                creditDto.getId(),
                creditDto.getUserId(),
                creditDto.getAccountId(),
                creditDto.getProposalNumber(),
                creditDto.getCreditKind()
        );
    }
    public CreditDto mapToCreditDtoFromCredit(final Credit credit){
        return new CreditDto(
                credit.getId(),
                credit.getUserId(),
                credit.getAccountId(),
                credit.getProposalNumber(),
                credit.getCreditKind()
        );
    }
}
