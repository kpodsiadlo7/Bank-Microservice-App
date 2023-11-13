package com.creditmanager.proposal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProposalFacade {
    public boolean checkingSalaryToCreditPossibilities(final ProposalDto proposalDto) {
        log.info("checking Salary To Credit Possibilities");
        double monthlyFee = proposalDto.getMonthlyFee();
        double salary = proposalDto.getSalary();
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
