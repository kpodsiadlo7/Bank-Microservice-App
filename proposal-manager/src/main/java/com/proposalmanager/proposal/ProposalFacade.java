package com.proposalmanager.proposal;

import com.proposalmanager.account.dto.AccountDto;
import com.proposalmanager.account.AccountFacade;
import com.proposalmanager.credit.dto.CreditDto;
import com.proposalmanager.credit.CreditFacade;
import com.proposalmanager.credit.enums.CreditKind;
import com.proposalmanager.proposal.dto.ProposalToTransfer;
import com.proposalmanager.proposal.enums.DescriptionRejected;
import com.proposalmanager.proposal.enums.StatusProposal;
import com.proposalmanager.transaction.TransactionFacade;
import com.proposalmanager.transfer.dto.TransferDto;
import com.proposalmanager.user.dto.UserDto;
import com.proposalmanager.user.UserFacade;
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
public class ProposalFacade {

    private final AdapterProposalEntityRepository adapterProposalEntityRepository;
    private final ProposalToTransfer proposalToTransfer;
    private final TransactionFacade transactionFacade;
    private final ProposalFactory proposalFactory;
    private final AccountFacade accountFacade;
    private final CreditFacade creditFacade;
    private final UserFacade userFacade;

    public boolean postProposal(final String proposalNumber) {
        log.info("post proposal");
        Proposal proposal = proposalFactory.mapToProposalFromProposalEntity
                (adapterProposalEntityRepository.findByProposalNumber(proposalNumber));
        log.info("account id from returning proposal from db"+proposal.getAccountId());

        if (!creditFacade.acceptCredit(proposal)) {
            log.info("proposal rejected");
            rejectedProposal(proposal);
            return false;
        }
        approveProposal(proposal);
        return true;
    }

    private void rejectedProposal(final Proposal proposalToRejected) {
        adapterProposalEntityRepository.save(proposalFactory.updateProposalEntity(proposalToRejected.toBuilder()
                .withDescriptionRejected(DescriptionRejected.CREDIT_EXCEEDS_YOUR_FINANCIAL_CAPACITY)
                .withStatusProposal(StatusProposal.REJECTED).build()));
    }

    private void approveProposal(final Proposal proposalToApproved) {
        log.info("approve proposal");
        TransferDto transferDto = proposalToTransfer.mapToTransferFromProposal(proposalToApproved);
        try {
            String accountNumber = accountFacade.getAccountByAccountId(transferDto.getAccountReceiveId()).getNumber();
            log.info("successful receive account from accounts manager");

            //deposit money
            String descriptionTransaction = "credit";
            transferDto.setAccountNumber(accountNumber);
            transactionFacade.makeTransaction(transferDto.getUserReceiveId(),
                    transferDto.getAccountReceiveId(), descriptionTransaction, transferDto);

            //reduce money by commission
            descriptionTransaction = "commission";
            transferDto.setAmount(BigDecimal.valueOf(proposalToApproved.getCommission()));
            transactionFacade.makeTransaction
                    (transferDto.getUserReceiveId(),
                            transferDto.getAccountReceiveId(), descriptionTransaction, transferDto);
            log.info("successful make transaction in transactions manager");
            adapterProposalEntityRepository.save(proposalFactory.updateProposalEntity(proposalToApproved.toBuilder()
                    .withDescriptionRejected(DescriptionRejected.ACCEPT)
                    .withAcceptStatement(true).withStatusProposal(StatusProposal.ACCEPT).build()));
            log.info("proposal successful saved");
        } catch (Exception ignored) {
        }
    }

    public Proposal getProposalByNumber(final String proposalNumber) {
        log.info("get proposal by number");
        if (!adapterProposalEntityRepository.existsByProposalNumber(proposalNumber)) {
            log.warn("proposal with given number doesn't exist");
            return Proposal.builder().withCurrency("error").withProposalNumber("Proposal with given number doesn't exist!").build();
        }
        return proposalFactory.mapToProposalFromProposalEntity
                (adapterProposalEntityRepository.findByProposalNumber(proposalNumber));
    }

    public Proposal validateProposalBeforePost(final Proposal proposal, final Long accountId, final String creditKind, final String promotion) {
        log.info("validate proposal before post");
        if (proposal.getAmountOfCredit() < 1000) {
            log.warn("error amount is less than 1000");
            log.warn("powinien byc 'error'"+Proposal.builder().withCurrency("error").build().toString());
            return Proposal.builder().withCurrency("error").withProposalNumber("Minimum credit amount is 1000").build();
        }
        if (promotion.equals("commission0") && proposal.getAmountOfCredit() > 10000) {
            log.warn("error promotion only for credit is less than 10000");
            return Proposal.builder().withCurrency("error").withProposalNumber("Promotion is only for credit which is below 10000").build();
        }

        CreditDto fetchingCredit = CreditDto.builder().build();
        AccountDto fetchingAccountDto = AccountDto.builder().build();
        UserDto fetchingUser = new UserDto();
        CreditKind kind = whatKindOfCredit(creditKind);

        fetchingCredit = checkAccountAlreadyHaveThatKindCredit(accountId, kind, fetchingCredit);
        if (fetchingCredit.getUserId() == -1) {
            log.warn("error " + fetchingCredit.getProposalNumber());
            return Proposal.builder().withCurrency("error").withProposalNumber(fetchingCredit.getProposalNumber()).build();
        }
        log.info("successful checking account if already have that kind credit(not have)");

        fetchingAccountDto = accountFacade.fetchAccountByAccountId(accountId, fetchingAccountDto);
        if (fetchingAccountDto.getUserId() == -1) {
            log.warn("error " + fetchingAccountDto.getAccountName());
            return Proposal.builder().withCurrency("error").withProposalNumber(fetchingAccountDto.getAccountName()).build();
        }
        log.info("successful fetching account by account id from accounts service");

        //checking if we already have money on account for commission
        BigDecimal commission = BigDecimal.valueOf(getCommission(proposal.getAmountOfCredit(), promotion));
        if (fetchingAccountDto.getBalance().compareTo(commission) < 0) {
            log.warn("don't enough money for paying commission");
            return Proposal.builder().withCurrency("error").withProposalNumber("You don't have enough money for paying commission which is " + commission + " " + fetchingAccountDto.getCurrencySymbol()).build();
        }

        fetchingUser = userFacade.fetchUserById(fetchingAccountDto.getUserId(), fetchingUser);
        if (fetchingUser.getId() == -1) {
            log.warn("error " + fetchingUser.getRealName());
            return Proposal.builder().withCurrency("error").withProposalNumber(fetchingUser.getRealName()).build();
        }

        Proposal proposalToSaveAfterPrepare = prepareProposal(fetchingUser, proposal, fetchingAccountDto, kind, promotion);
        log.info("save proposal");
        return proposalFactory.mapToProposalFromProposalEntity
                (adapterProposalEntityRepository.save
                        (proposalFactory.mapToProposalEntityFromProposal(proposalToSaveAfterPrepare)));

    }

    public CreditDto checkAccountAlreadyHaveThatKindCredit(final Long accountId, final CreditKind creditKind, CreditDto fetchingCreditDto) {
        log.info("check Account Already Have That Kind Credit");
        boolean response;
        try {
            response = creditFacade.checkAccountAlreadyHaveThatKindCredit(accountId, creditKind);
        } catch (Exception e) {
            log.warn("problem with connecting to credit manager");
            return CreditDto.builder().withUserId(-1L).withProposalNumber("Problem with connecting to credit-manager").build();
        }
        if (existsByAccountIdAndStatusProposal(accountId, StatusProposal.OPEN)) {
            log.warn("You already have open proposal for that kind of credit");
            return CreditDto.builder().withUserId(-1L).withProposalNumber("You already have open proposal").build();
        }
        if (response && !existsByAccountIdAndStatusProposal(accountId,StatusProposal.REJECTED) ||
                existsByAccountIdAndStatusProposal(accountId,StatusProposal.ACCEPT)) {
            log.warn("You already have that kind of credit on this account");
            return CreditDto.builder().withUserId(-1L).withProposalNumber("You already have that kind of credit on this account").build();
        }
        // this user id is completely not needed for anything, I set it only for validation
        log.info("this account not have that kind credit");
        return CreditDto.builder().withUserId(7L).build();
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
        return proposalFactory.mapToProposalSetFromProposalEntitySet
                (adapterProposalEntityRepository.findAllByUserId(userId));
    }

    private Proposal prepareProposal(final UserDto fetchingUserDto, final Proposal proposal, final AccountDto fetchingAccountDto, final CreditKind creditKind, final String promotion) {
        log.info("prepare proposal");
        double interest = createInterest(proposal.getAmountOfCredit(),
                proposal.getMonth(),
                fetchingAccountDto.getAccountName());
        double commission = getCommission(proposal.getAmountOfCredit(), promotion);

        double monthlyFee = createMonthlyFee(
                proposal.getAmountOfCredit(),
                proposal.getMonth(),
                interest);
        log.info("prepare proposal one step before return proposal");

        return proposalFactory.mapToProposalFromProposalDto(proposalFactory.buildProposalDtoFromProposal(
                (new Proposal(
                        proposal.getId(),
                        fetchingUserDto.getId(),
                        fetchingAccountDto.getId(),
                        proposal.getAmountOfCredit(),
                        proposal.getMonth(),
                        proposal.getSalary(),
                        interest,
                        commission,
                        monthlyFee,
                        fetchingAccountDto.getCurrency(),
                        fetchingAccountDto.getCurrencySymbol(),
                        fetchingUserDto.getRealName(),
                        proposal.getPurpose(),
                        createApplicationNumber(),
                        LocalDate.now(),
                        LocalDate.now().plusMonths(proposal.getMonth()),
                        false,
                        StatusProposal.OPEN,
                        DescriptionRejected.OPEN,
                        creditKind))));
    }

    public boolean existsByAccountIdAndStatusProposal(Long accountId, StatusProposal statusProposal) {
        return adapterProposalEntityRepository.existsByAccountIdAndStatusProposal(accountId, StatusProposal.OPEN);
    }
}
