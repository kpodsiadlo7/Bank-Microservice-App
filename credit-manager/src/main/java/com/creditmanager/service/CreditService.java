package com.creditmanager.service;

import com.creditmanager.domain.enums.CreditKind;
import com.creditmanager.repository.adapter.AdapterCreditEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final AdapterCreditEntityRepository adapterCreditEntityRepository;

    public boolean checkAccountAlreadyHaveThatKindCredit(final Long accountId, final CreditKind creditKind) {
        return adapterCreditEntityRepository.existsByAccountIdAndCreditKind(accountId, creditKind);
    }


   // public validateBeforeCredit() {
      //  if (!checkingSalaryToCreditPossibilities()){
            // then continue
    //    }
  //  }

    //private boolean checkingSalaryToCreditPossibilities(final Proposal proposal) {
       // if(proposal.getAmountOfCredit() * 0.2 > proposal.getSalary()){
            //int random = (int) (Math.random() * 7);
           // if (random < 2){
                //then rejected proposal
            //}
        //}

        //return ;
   // }
}
