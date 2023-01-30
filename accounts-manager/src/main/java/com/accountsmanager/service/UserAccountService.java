package com.accountsmanager.service;

import com.accountsmanager.domain.UserAccountEntity;
import com.accountsmanager.repository.adapter.AdapterUserAccountRepository;
import com.accountsmanager.service.data.Transfer;
import com.accountsmanager.service.data.UserAccount;
import com.accountsmanager.service.mapper.UserAccountsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountsMapper userAccountsMapper;
    private final AdapterUserAccountRepository adapterUserAccountRepository;

    public UserAccount validateData(final Long userId, final UserAccount userAccount) {
        if (userAccount.getCurrency() == null)
            return createMainAccount(userId, userAccount);
        return createAccountForUser(userId, userAccount);
    }

    private UserAccount createAccountForUser(final Long userId, final UserAccount userAccount) {
        userAccount.setUserId(userId);
        userAccount.setNumber(prepareAccountData(userAccount).getNumber());
        log.info("wyjeżdża z currency: " + userAccount.getCurrency());
        UserAccountEntity accountEntity = userAccountsMapper.mapToUserAccountEntityFromUserAccount(userAccount);
        adapterUserAccountRepository.save(accountEntity);
        return userAccountsMapper.mapToUserAccountFromUserAccountEntity(accountEntity);
    }


    private UserAccount prepareAccountData(final UserAccount userAccount) {
        //start money
        log.info("money before depositMoney: "+userAccount.getBalance());
        userAccount.setBalance(depositMoney(10000));
        log.info("money after depositMoney: "+userAccount.getBalance());
        return createNumberForAccount(userAccount);
    }

    private UserAccount createNumberForAccount(final UserAccount userAccount) {
        String symbol = "";
        switch (userAccount.getCurrency()) {
            case "USD" -> {
                symbol = "US77";
                userAccount.setCurrencySymbol("$");
            }
            case "EUR" -> {
                symbol = "EU49";
                userAccount.setCurrencySymbol("€");
            }
            case "GBP" -> {
                symbol = "GB90";
                userAccount.setCurrencySymbol("£");
            }
            case "PLN" -> {
                symbol = "PL55";
                userAccount.setCurrencySymbol("zł");
            }
        }
        Random random = new Random();
        StringBuilder b = new StringBuilder();
        b.append(symbol);
        for (int i = 0; i <= 20; i++) {
            b.append(random.nextInt(7));
        }
        if (adapterUserAccountRepository.existsByNumber(b.toString())) {
            b.setLength(0);
            return prepareAccountData(userAccount);
        }
        userAccount.setNumber(b.toString());
        return userAccount;
    }

    private BigDecimal depositMoney(final int moneyToDeposit) {
        return new BigDecimal(moneyToDeposit);
    }

    private BigDecimal withdrawMoney(final int moneyToWithdraw) {
        return new BigDecimal(moneyToWithdraw);
    }

    public List<UserAccount> getAllUserAccounts(final Long userId) {
        log.info("get all user accounts start method and user id: " + userId);
        List<UserAccountEntity> accountEntities = adapterUserAccountRepository.findAllByUserId(userId);
        log.info("quantity accounts from db: " + accountEntities.size());
        return userAccountsMapper.mapToUserAccountListFromUserAccountEntityList(accountEntities);
    }

    public Transfer validateDataBeforeTransaction(final Long userId, final Transfer transfer) {
        if (transfer.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            transfer.setUserAccountNumber("Minimum amount must be over 0");
            transfer.setAmount(new BigDecimal(-1));
            return transfer;
        }
        if (!adapterUserAccountRepository.existsByNumber(transfer.getUserAccountNumber())) {
            transfer.setAmount(new BigDecimal(-1));
            transfer.setUserAccountNumber("User with this number doesn't exist!");
            return transfer;
        }
        if (adapterUserAccountRepository.findByUserId(userId).getBalance().compareTo(transfer.getAmount()) < 0) {
            transfer.setAmount(new BigDecimal(-1));
            transfer.setUserAccountNumber("You don't have enough money");
            return transfer;
        }

        createTransaction(userId, transfer);
        return transfer;
    }

    @Transactional
    protected void createTransaction(final Long userId, final Transfer transfer) {
        increaseMoney(transfer);
        decreaseMoney(userId, transfer.getAmount());
    }

    private void increaseMoney(final Transfer transfer) {
        UserAccount userAccountToReceiveMoney = userAccountsMapper.mapToUserAccountFromUserAccountEntity
                (adapterUserAccountRepository.findByNumber(transfer.getUserAccountNumber()));
        log.info("balance before increase: " + userAccountToReceiveMoney.getBalance());
        BigDecimal amountAfterIncrease = userAccountToReceiveMoney.getBalance().add(transfer.getAmount());
        log.info("balance after increase: " + amountAfterIncrease);
        userAccountToReceiveMoney.setBalance(amountAfterIncrease);
        adapterUserAccountRepository.save(userAccountsMapper.updateUserAccountEntityFromUserAccount(userAccountToReceiveMoney));
    }

    private void decreaseMoney(final Long userId, final BigDecimal amountToSubtract) {
        UserAccount userAccountToSpendMoney = userAccountsMapper.mapToUserAccountFromUserAccountEntity
                (adapterUserAccountRepository.findByUserId(userId));
        log.info("balance before decrease: " + userAccountToSpendMoney.getBalance());
        BigDecimal amountAfterDecrease = userAccountToSpendMoney.getBalance().subtract(amountToSubtract);
        log.info("balance after decrease: " + amountAfterDecrease);
        userAccountToSpendMoney.setBalance(amountAfterDecrease);
        adapterUserAccountRepository.save(userAccountsMapper.updateUserAccountEntityFromUserAccount(userAccountToSpendMoney));
    }

    private UserAccount createMainAccount(final Long userId, final UserAccount userAccount) {
        userAccount.setUserId(userId);
        userAccount.setAccountName("Main account");
        userAccount.setCurrency("PLN");
        userAccount.setNumber(prepareAccountData(userAccount).getNumber());
        adapterUserAccountRepository.save(userAccountsMapper.mapToUserAccountEntityFromUserAccount(userAccount));
        return userAccount;
    }
}
