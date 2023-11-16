package com.creditmanager.proposal;

import com.creditmanager.proposal.dto.ProposalDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProposalFacade {
    private final ProposalFactory proposalFactory;

    public boolean checkingSalaryToCreditPossibilities(final ProposalDto proposalDto) {
        Proposal proposal = proposalFactory.fromPorposalDtoToProposal(proposalDto);
        double monthlyFee = proposal.getMonthlyFee();
        double salary = proposal.getSalary();
        int random = (int) (Math.random() * 9);

        if(monthlyFee >= salary){
            return false;
        }
        if(monthlyFee >= salary/1.25){
            return random >= 8;
        }
        if(monthlyFee > salary/2){
            return random >= 4;
        }
        return true;
    }
}
