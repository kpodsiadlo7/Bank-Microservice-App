package com.creditmanager.proposal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProposalFacade {
    public boolean checkingSalaryToCreditPossibilities(final ProposalDto proposalDto) {
        log.info("checking Salary To Credit Possibilities");

        double salary = proposalDto.getSalary();
        double monthlyFee = proposalDto.getMonthlyFee();

        if (monthlyFee >= salary) {
            return false;
        }
        double salaryToFeeRatio = salary / monthlyFee;

        if (monthlyFee >= salaryToFeeRatio * 1.25) {
            int random = (int) (Math.random() * 9);
            return random >= 8;
        }
        if (monthlyFee > salaryToFeeRatio * 0.5) {
            int random = (int) (Math.random() * 9);
            return random >= 4;
        }
        return true;
    }
}
