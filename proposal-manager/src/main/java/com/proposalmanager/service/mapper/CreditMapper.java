package com.proposalmanager.service.mapper;

import com.proposalmanager.service.data.Credit;
import com.proposalmanager.web.dto.CreditDto;
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
