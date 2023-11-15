package com.proposalmanager.proposal;

import com.proposalmanager.account.Account;
import com.proposalmanager.account.AccountService;
import com.proposalmanager.credit.Credit;
import com.proposalmanager.credit.CreditService;
import com.proposalmanager.domain.enums.CreditKind;
import com.proposalmanager.domain.enums.DescriptionRejected;
import com.proposalmanager.domain.enums.StatusProposal;
import com.proposalmanager.adapter.ProposalToTransfer;
import com.proposalmanager.transfer.TransferMapper;
import com.proposalmanager.transfer.Transfer;
import com.proposalmanager.user.User;
import com.proposalmanager.user.UserService;
import com.proposalmanager.account.FeignServiceAccountsManager;
import com.proposalmanager.credit.FeignServiceCreditManager;
import com.proposalmanager.transaction.FeignServiceTransactionsManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public boolean postProposal(final String proposalNumber) {
        log.info("post proposal");
        Proposal proposal = proposalMapper.mapToProposalFromProposalEntity
                (adapterProposalEntityRepository.findByProposalNumber(proposalNumber));
        log.info("account id from returning proposal from db"+proposal.getAccountId());

        if (!feignServiceCreditManager.acceptCredit(proposal)) {
            log.info("proposal rejected");
            rejectedProposal(proposal);
            return false;
        }
        approveProposal(proposal);
        return true;
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
            transfer.setAmount(BigDecimal.valueOf(proposalToApproved.getCommission()));
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

    public Proposal validateProposalBeforePost(final Proposal proposal, final Long accountId, final String creditKind, final String promotion) {
        log.info("validate proposal before past");
        Proposal error = new Proposal();
        if (proposal.getAmountOfCredit() < 1000) {
            log.warn("error amount is less than 1000");
            error.setCurrency("error");
            error.setProposalNumber("Minimum credit amount is 1000");
            return error;
        }
        if (promotion.equals("commission0") && proposal.getAmountOfCredit() > 10000) {
            log.warn("error promotion only for credit is less than 10000");
            error.setCurrency("error");
            error.setProposalNumber("Promotion is only for credit which is below 10000");
            return error;
        }

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
        BigDecimal commission = BigDecimal.valueOf(getCommission(proposal.getAmountOfCredit(), promotion));
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

        Proposal proposalToSaveAfterPrepare = prepareProposal(fetchingUser, proposal, fetchingAccount, kind, promotion);
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
        double monthlyFee = (amountOfCredit + interest) / month;
        return Math.round(monthlyFee * 100.0) / 100.0;
    }

    private double getCommission(final double amountOfCredit, final String promotion) {
        log.info("get commission");
        if (promotion.equals("commission0")) return 0;

        double commission = amountOfCredit * 0.0024;
        if (commission < 50)
            return 50;
        return Math.ceil(commission);
    }

    private double createInterest(final double amountOfCredit, final int month, final String accountType) {
        log.info("crete interest");
        if (accountType.equals("Student"))
            return 0;

        if (month <= 24)
            return Math.ceil(amountOfCredit * (19.4 / 100));
        if (month <= 48)
            return Math.ceil(amountOfCredit * (30.3 / 100));
        if (month <= 60)
            return Math.ceil(amountOfCredit * (37.4 / 100));
        if (month <= 72)
            return Math.ceil(amountOfCredit * (55.9 / 100));
        if (month <= 120)
            return Math.ceil(amountOfCredit * (74.1 / 100));

        return Math.ceil(amountOfCredit * (139.7 / 100));
    }

    public Set<Proposal> getAllProposalsByUserId(final Long userId) {
        return proposalMapper.mapToProposalSetFromProposalEntitySet
                (adapterProposalEntityRepository.findAllByUserId(userId));
    }

    private Proposal prepareProposal(final User fetchingUser, final Proposal proposal, final Account fetchingAccount, final CreditKind creditKind, final String promotion) {
        log.info("prepare proposal");
        double interest = createInterest(proposal.getAmountOfCredit(),
                proposal.getMonth(),
                fetchingAccount.getAccountName());
        double commission = getCommission(proposal.getAmountOfCredit(), promotion);

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
}
