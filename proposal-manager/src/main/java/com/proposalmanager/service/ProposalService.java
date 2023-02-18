package com.proposalmanager.service;

import com.proposalmanager.domain.enums.CreditKind;
import com.proposalmanager.domain.enums.DescriptionRejected;
import com.proposalmanager.domain.enums.StatusProposal;
import com.proposalmanager.repository.adapter.AdapterProposalEntityRepository;
import com.proposalmanager.service.adapter.ProposalToTransfer;
import com.proposalmanager.service.data.*;
import com.proposalmanager.service.mapper.ProposalMapper;
import com.proposalmanager.service.mapper.TransferMapper;
import com.proposalmanager.web.feign.FeignServiceAccountsManager;
import com.proposalmanager.web.feign.FeignServiceCreditManager;
import com.proposalmanager.web.feign.FeignServiceTransactionsManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.Random;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProposalService {

    private final AdapterProposalEntityRepository adapterProposalEntityRepository;
    private final FeignServiceTransactionsManager feignServiceTransactionsManager;
    private final FeignServiceAccountsManager feignServiceAccountsManager;
    private final FeignServiceCreditManager feignServiceCreditManager;
    private final ProposalToTransfer proposalToTransfer;
    private final AccountService accountService;
    private final ProposalMapper proposalMapper;
    private final TransferMapper transferMapper;
    private final CreditService creditService;
    private final UserService userService;

    public void postProposal(final String proposalNumber) {
        log.info("post proposal");
        Proposal proposal = proposalMapper.mapToProposalFromProposalEntity
                (adapterProposalEntityRepository.findByProposalNumber(proposalNumber));

        if (!feignServiceCreditManager.acceptCredit(proposal)) {
            log.info("proposal rejected");
            rejectedProposal(proposal);
            return;
        }
        approveProposal(proposal);
    }

    private void rejectedProposal(final Proposal proposalToRejected) {
        proposalToRejected.setDescriptionRejected(DescriptionRejected.CREDIT_EXCEEDS_YOUR_FINANCIAL_CAPACITY);
        proposalToRejected.setStatusProposal(StatusProposal.REJECTED);
        adapterProposalEntityRepository.save(proposalMapper.updateProposalEntity(proposalToRejected));
    }

    private void approveProposal(final Proposal proposalToApproved) {
        log.info("approve proposal");
        Transfer transfer = proposalToTransfer.mapToTransferFromProposal(proposalToApproved);
        try {
            String accountNumber = feignServiceAccountsManager.getAccountByAccountId(transfer.getAccountReceiveId()).getNumber();
            log.info("successful receive account from accounts manager");

            //deposit money
            String descriptionTransaction = "credit";
            transfer.setAccountNumber(accountNumber);
            feignServiceTransactionsManager.makeTransaction(transfer.getUserReceiveId(), transfer.getAccountReceiveId(), descriptionTransaction,
                    transferMapper.mapToTransferDtoFromTransfer(transfer));

            //reduce money by commission
            descriptionTransaction = "commission";
            transfer.setAmount(BigDecimal.valueOf(getCommission(proposalToApproved.getAmountOfCredit())));
            feignServiceTransactionsManager.makeTransaction(transfer.getUserReceiveId(), transfer.getAccountReceiveId(), descriptionTransaction,
                    transferMapper.mapToTransferDtoFromTransfer(transfer));

            log.info("successful make transaction in transactions manager");
            proposalToApproved.setDescriptionRejected(DescriptionRejected.ACCEPT);
            proposalToApproved.setStatusProposal(StatusProposal.ACCEPT);
            proposalToApproved.setAcceptStatement(true);
            adapterProposalEntityRepository.save(proposalMapper.updateProposalEntity(proposalToApproved));
            log.info("proposal successful saved");
        } catch (Exception ignored) {
        }
    }

    public Proposal getProposalByNumber(final String proposalNumber) {
        log.info("get proposal by number");
        if (!adapterProposalEntityRepository.existsByProposalNumber(proposalNumber)) {
            Proposal error = new Proposal();
            log.warn("proposal with given number doesn't exist");
            error.setCurrency("error");
            error.setProposalNumber("Proposal with given number doesn't exist!");
            return error;
        }
        return proposalMapper.mapToProposalFromProposalEntity
                (adapterProposalEntityRepository.findByProposalNumber(proposalNumber));
    }

    public Proposal validateProposalBeforePost(final Proposal proposal, final Long accountId, final String creditKind) {
        log.info("validate proposal before past");
        Proposal error = new Proposal();
        Credit fetchingCredit = new Credit();
        Account fetchingAccount = new Account();
        User fetchingUser = new User();
        CreditKind kind = whatKindOfCredit(creditKind);

        fetchingCredit = creditService.checkAccountAlreadyHaveThatKindCredit(accountId, kind, fetchingCredit);
        if (fetchingCredit.getUserId() == -1) {
            log.warn("error " + fetchingCredit.getProposalNumber());
            error.setCurrency("error");
            error.setProposalNumber(fetchingCredit.getProposalNumber());
            return error;
        }
        log.info("successful checking account if already have that kind credit(not have)");

        fetchingAccount = accountService.fetchAccountByAccountId(accountId, fetchingAccount);
        if (fetchingAccount.getUserId() == -1) {
            log.warn("error " + fetchingAccount.getAccountName());
            error.setCurrency("error");
            error.setProposalNumber(fetchingAccount.getAccountName());
            return error;
        }
        log.info("successful fetching account by account id from accounts service");

        //checking if we already have money on account for commission
        BigDecimal commission = BigDecimal.valueOf(getCommission(proposal.getAmountOfCredit()));
        if (fetchingAccount.getBalance().compareTo(commission) < 0) {
            log.warn("don't enough money for paying commission");
            error.setCurrency("error");
            error.setProposalNumber("You don't have enough money for paying commission which is " + commission + " " + fetchingAccount.getCurrencySymbol());
            return error;
        }

        fetchingUser = userService.fetchUserById(fetchingAccount.getUserId(), fetchingUser);
        if (fetchingUser.getId() == -1) {
            log.warn("error " + fetchingUser.getRealName());
            error.setCurrency("error");
            error.setProposalNumber(fetchingUser.getRealName());
            return error;
        }


        Proposal proposalToSaveAfterPrepare = prepareProposal(fetchingUser, proposal, fetchingAccount, kind);
        log.info("save proposal");
        return proposalMapper.mapToProposalFromProposalEntity
                (adapterProposalEntityRepository.save
                        (proposalMapper.mapToProposalEntityFromProposal(proposalToSaveAfterPrepare)));

    }

    private CreditKind whatKindOfCredit(final String creditKind) {
        log.info("what kind of credit");
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
                        LocalDate.now().plusMonths(proposal.getMonth()),
                        false,
                        StatusProposal.OPEN,
                        DescriptionRejected.OPEN,
                        creditKind))));
    }

    private String createApplicationNumber() {
        log.info("create application number");
        Random random = new Random();
        StringBuilder b = new StringBuilder();
        for (int i = 0; i <= 20; i++) {
            b.append(random.nextInt(7));
        }
        if (adapterProposalEntityRepository.existsByProposalNumber(b.toString())) {
            log.info("the random number exist in db");
            b.setLength(0);
            return createApplicationNumber();
        }
        return b.toString();
    }

    private double createMonthlyFee(final double amountOfCredit, final int month, double interest) {
        log.info("create monthly fee");
        double monthlyFee = (amountOfCredit+interest)/month;
        return Math.round(monthlyFee*100.0)/100.0;
    }

    private double getCommission(final double amountOfCredit) {
        log.info("get commission");
        double commission = amountOfCredit * 0.0024f;
        if (commission < 50)
            return 50f;
        return Math.ceil(commission);
    }

    private double createInterest(final double amountOfCredit, final int month, final String accountType) {
        log.info("crete interest");
        if (accountType.equals("Student"))
            return 0f;

        if (month <= 24)
            return Math.ceil(amountOfCredit * (19.4f / 100));
        if (month <= 48)
            return Math.ceil(amountOfCredit * (30.3f / 100));
        if (month <= 60)
            return Math.ceil(amountOfCredit * (37.4f / 100));
        if (month <= 72)
            return Math.ceil(amountOfCredit * (55.9f / 100));
        if (month <= 120)
            return Math.ceil(amountOfCredit * (74.1f / 100));

        return Math.ceil(amountOfCredit * (139.7f / 100));
    }

    public Set<Proposal> getAllProposalsByUserId(final Long userId) {
        return proposalMapper.mapToProposalSetFromProposalEntitySet
                (adapterProposalEntityRepository.findAllByUserId(userId));
    }
}
