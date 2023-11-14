package com.mainapp.proposal;

import com.mainapp.proposal.dto.DataForProposalDto;
import com.mainapp.proposal.dto.ProposalDto;
import org.springframework.stereotype.Service;

@Service
class ProposalFactory {

    ProposalDto buildProposalDtoFromDataForProposalDto(final DataForProposalDto data){
        return ProposalDto.builder()
                .withMonth(data.getMonth())
                .withSalary(data.getSalary())
                .withAmountOfCredit(data.getAmountOfCredit())
                .withPurpose(data.getPurpose()).build();
    }
}
