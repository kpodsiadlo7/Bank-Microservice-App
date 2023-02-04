package com.proposalmanager.service;

import com.proposalmanager.domain.enums.CreditKind;
import com.proposalmanager.domain.enums.DescriptionRejected;
import com.proposalmanager.domain.enums.StatusProposal;
import com.proposalmanager.repository.adapter.AdapterProposalEntityRepository;
import com.proposalmanager.service.data.Account;
import com.proposalmanager.service.data.Credit;
import com.proposalmanager.service.data.Proposal;
import com.proposalmanager.service.data.User;
import com.proposalmanager.service.mapper.AccountMapper;
import com.proposalmanager.service.mapper.CreditMapper;
import com.proposalmanager.service.mapper.ProposalMapper;
import com.proposalmanager.service.mapper.UserMapper;
import com.proposalmanager.web.feign.FeignServiceAccountsManager;
import com.proposalmanager.web.feign.FeignServiceUserManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProposalService {

    private final AdapterProposalEntityRepository adapterProposalEntityRepository;
    private final FeignServiceAccountsManager feignServiceAccountsManager;
    private final FeignServiceUserManager feignServiceUserManager;
    private final ProposalMapper proposalMapper;
    private final AccountMapper accountMapper;
    private final CreditService creditService;
    private final CreditMapper creditMapper;
    private final UserMapper userMapper;

    public Proposal getProposalByNumber(final String proposalNumber) {
        if (!adapterProposalEntityRepository.existsByProposalNumber(proposalNumber)) {
            Proposal error = new Proposal();
            error.setCurrency("error");
            error.setProposalNumber("Proposal with given number doesn't exist!");
            return error;
        }
        return proposalMapper.mapToProposalFromProposalEntity
                (adapterProposalEntityRepository.findByProposalNumber(proposalNumber));
    }

    public Proposal validateProposalBeforePost(final Proposal proposal, final Long accountId, final String creditKind) {
        Proposal error = new Proposal();
        Account fetchingAccount = new Account();
        Credit fetchingCredit = new Credit();
        User fetchingUser = new User();
        CreditKind kind = whatKindOfCredit(creditKind);


        fetchingCredit = creditService.checkAccountAlreadyHaveThatKindCredit(accountId,kind,fetchingCredit);
        if (fetchingCredit.getUserId() == -1){
            error.setCurrency("error");
            error.setProposalNumber(fetchingCredit.getProposalNumber());
            return error;
        }



        /**
         * needs to be refactored
         */
        log.info("validate proposal before post method start");
        try {
            fetchingAccount = accountMapper.mapToUserAccountFromUserAccountDto
                    (feignServiceAccountsManager.getAccountByAccountId(accountId));
        } catch (Exception e) {
            error.setCurrency("error");
            error.setProposalNumber("There is a problem with connecting to account manager");
            return error;
        }

        try {
            fetchingUser = userMapper.mapToUserFromUserDto(feignServiceUserManager.getUserById(fetchingAccount.getUserId()));
        } catch (Exception e){
            error.setCurrency("error");
            error.setProposalNumber("There is a problem with connecting to user manager");
            return error;
        }
        log.info("validate proposal before post method before prepare proposal");

        Proposal proposalToSaveAfterPrepare = prepareProposal(fetchingUser, proposal, fetchingAccount, kind);

        return proposalMapper.mapToProposalFromProposalEntity
                (adapterProposalEntityRepository.save
                        (proposalMapper.mapToProposalEntityFromProposal(proposalToSaveAfterPrepare)));

    }

    private CreditKind whatKindOfCredit(final String creditKind) {
        switch (creditKind) {
            case "MORTGAGE" -> {
                return CreditKind.MORTGAGE;
            }
            case "DEBIT" -> {
                return CreditKind.DEBIT;
            }
        }
        return CreditKind.CASH;
    }

    private Proposal prepareProposal(final User fetchingUser, final Proposal proposal, final Account fetchingAccount, final CreditKind creditKind) {
        log.info("prepare proposal");
        double interest = createInterest(proposal.getAmountOfCredit(),
                proposal.getMonth(),
                fetchingAccount.getAccountName());
        double commission = getCommission(proposal.getAmountOfCredit());
        double monthlyFee = createMonthlyFee(
                proposal.getAmountOfCredit(),
                proposal.getMonth(),
                interest);
        log.info("prepare proposal one step before return proposal");

        return proposalMapper.mapToProposalFromProposalDto(proposalMapper.mapToProposalDtoFromProposal(
                (new Proposal(
                        proposal.getId(),
                        fetchingUser.getId(),
                        fetchingAccount.getId(),
                        proposal.getAmountOfCredit(),
                        proposal.getMonth(),
                        proposal.getSalary(),
                        interest,
                        commission,
                        monthlyFee,
                        fetchingAccount.getCurrency(),
                        fetchingAccount.getCurrencySymbol(),
                        fetchingUser.getRealName(),
                        proposal.getPurpose(),
                        createApplicationNumber(),
                        LocalDate.now(),
                        false,
                        StatusProposal.OPEN,
                        DescriptionRejected.OPEN,
                        creditKind
                ))));
    }

    private String createApplicationNumber() {
        Random random = new Random();
        StringBuilder b = new StringBuilder();
        for (int i = 0; i <= 20; i++) {
            b.append(random.nextInt(7));
        }
        if (adapterProposalEntityRepository.existsByProposalNumber(b.toString())) {
            b.setLength(0);
            return createApplicationNumber();
        }
        return b.toString();
    }

    private double createMonthlyFee(final double amountOfCredit, final int month, double interest) {
        return (amountOfCredit+interest)/month;
    }

    private double getCommission(final double amountOfCredit) {
        double commission = amountOfCredit * 0.0024f;
        if (commission < 50)
            return 50f;
        return Math.ceil(commission);
    }

    private double createInterest(final double amountOfCredit, final int month, final String accountType) {
        double interest = 0.f;
        if (accountType.equals("Student"))
            return 0f;

        if (month <= 24)
            return interest = Math.ceil(amountOfCredit * (19.4f / 100));
        if (month <= 48)
            return interest = Math.ceil(amountOfCredit * (30.3f / 100));
        if (month <= 60)
            return interest = Math.ceil(amountOfCredit * (37.4f / 100));
        if (month <= 72)
            return interest = Math.ceil(amountOfCredit * (55.9f / 100));
        if (month <= 120)
            return interest = Math.ceil(amountOfCredit * (74.1f / 100));

        return Math.ceil(amountOfCredit * (139.7f / 100));
    }

}
