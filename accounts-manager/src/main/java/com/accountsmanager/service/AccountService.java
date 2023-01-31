package com.accountsmanager.service;

import com.accountsmanager.domain.AccountEntity;
import com.accountsmanager.repository.adapter.AdapterAccountRepository;
import com.accountsmanager.service.data.Account;
import com.accountsmanager.service.data.Transfer;
import com.accountsmanager.service.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountMapper accountMapper;
    private final AdapterAccountRepository adapterAccountRepository;

    public Account validateData(final Long userId, final Account account) {
        if (account.getCurrency() == null)
            return createMainAccount(userId, account);
        return createAccountForUser(userId, account);
    }

    private Account createAccountForUser(final Long userId, final Account account) {
        account.setUserId(userId);
        account.setNumber(prepareAccountData(account).getNumber());
        log.info("wyjeżdża z currency: " + account.getCurrency());
        AccountEntity accountEntity = accountMapper.mapToUserAccountEntityFromUserAccount(account);
        adapterAccountRepository.save(accountEntity);
        return accountMapper.mapToUserAccountFromUserAccountEntity(accountEntity);
    }


    private Account prepareAccountData(final Account account) {
        log.info("money before depositMoney: " + account.getBalance());
        //start money
        account.setBalance(new BigDecimal(10000));
        log.info("money after depositMoney: " + account.getBalance());
        return createNumberForAccount(account);
    }

    private Account createNumberForAccount(final Account account) {
        String symbol = "";
        switch (account.getCurrency()) {
            case "USD" -> {
                symbol = "US77";
                account.setCurrencySymbol("$");
            }
            case "EUR" -> {
                symbol = "EU49";
                account.setCurrencySymbol("€");
            }
            case "GBP" -> {
                symbol = "GB90";
                account.setCurrencySymbol("£");
            }
            case "PLN" -> {
                symbol = "PL55";
                account.setCurrencySymbol("zł");
            }
        }
        Random random = new Random();
        StringBuilder b = new StringBuilder();
        b.append(symbol);
        for (int i = 0; i <= 20; i++) {
            b.append(random.nextInt(7));
        }
        if (adapterAccountRepository.existsByNumber(b.toString())) {
            b.setLength(0);
            return prepareAccountData(account);
        }
        account.setNumber(b.toString());
        return account;
    }

    public Transfer depositMoney(final Long thisAccountId, final Transfer transfer) {
        if (!Objects.equals(validateDataBeforeTransaction(thisAccountId, transfer).getAmount(), new BigDecimal(-1)))
            increaseMoney(thisAccountId, transfer.getAmount());
        return transfer;
    }

    public Transfer withdrawMoney(final Long thisAccountId, final Transfer transfer) {
        if (!Objects.equals(validateDataBeforeTransaction(thisAccountId, transfer).getAmount(), new BigDecimal(-1)))
            decreaseMoney(thisAccountId, transfer.getAmount());
        return transfer;
    }

    public List<Account> getAllUserAccounts(final Long userId) {
        log.info("get all user accounts start method and user id: " + userId);
        List<AccountEntity> accountEntities = adapterAccountRepository.findAllByUserId(userId);
        log.info("quantity accounts from db: " + accountEntities.size());
        return accountMapper.mapToUserAccountListFromUserAccountEntityList(accountEntities);
    }

    public Transfer moneyTransferFromUserToUser(final Long thisAccountId, final Transfer transfer) {
        if (!Objects.equals(validateDataBeforeTransaction(thisAccountId, transfer).getAmount(), new BigDecimal(-1)))
            return closeMoneyTransfer(thisAccountId, transfer);
        return transfer;
    }

    public Transfer validateDataBeforeTransaction(final Long thisAccountId, final Transfer transfer) {
        if (transfer.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            transfer.setUserAccountNumber("Minimum amount must be over 0");
            transfer.setAmount(new BigDecimal(-1));
            return transfer;
        }
        if (adapterAccountRepository.findById(thisAccountId).getBalance().compareTo(transfer.getAmount()) < 0) {
            if (transfer.getUserAccountNumber().equals("deposit"))
                return transfer;
            transfer.setAmount(new BigDecimal(-1));
            transfer.setUserAccountNumber("You don't have enough money");
            return transfer;
        }
        if (adapterAccountRepository.findById(thisAccountId).getNumber().equals(transfer.getUserAccountNumber())) {
            transfer.setAmount(new BigDecimal(-1));
            transfer.setUserAccountNumber("You can't send money to account which from you send");
            return transfer;
        }
        if (!adapterAccountRepository.existsByNumber(transfer.getUserAccountNumber())) {
            if (transfer.getUserAccountNumber().equals("deposit") ||
                    transfer.getUserAccountNumber().equals("withdraw"))
                return transfer;
            transfer.setAmount(new BigDecimal(-1));
            transfer.setUserAccountNumber("User with this number doesn't exist!");
            return transfer;
        }
        return transfer;
    }

    @Transactional
    protected Transfer closeMoneyTransfer(final Long thisAccountId, final Transfer transfer) {
        Long accountIncreaseId = adapterAccountRepository.findByNumber(transfer.getUserAccountNumber()).getId();
        log.info("should be 1 before set " + accountIncreaseId);
        log.info("account nr " + transfer.getUserAccountNumber());
        increaseMoney(accountIncreaseId, transfer.getAmount());
        decreaseMoney(thisAccountId, transfer.getAmount());

        transfer.setAccountReceiveId(accountIncreaseId);
        log.info("should be 1 after set " + transfer.getAccountReceiveId());
        return transfer;
    }

    protected void increaseMoney(final Long thisAccountId, final BigDecimal amount) {
        Account accountToReceiveMoney = accountMapper.mapToUserAccountFromUserAccountEntity
                (adapterAccountRepository.findById(thisAccountId));

        log.info("balance before increase: " + accountToReceiveMoney.getBalance());
        BigDecimal amountAfterIncrease = accountToReceiveMoney.getBalance().add(amount);
        log.info("balance after increase: " + amountAfterIncrease);
        accountToReceiveMoney.setBalance(amountAfterIncrease);
        adapterAccountRepository.save(accountMapper.updateUserAccountEntityFromUserAccount(accountToReceiveMoney));
    }

    protected void decreaseMoney(final Long thisAccountId, final BigDecimal amount) {
        Account accountToSpendMoney = accountMapper.mapToUserAccountFromUserAccountEntity
                (adapterAccountRepository.findById(thisAccountId));
        log.info("balance before decrease: " + accountToSpendMoney.getBalance());
        BigDecimal amountAfterDecrease = accountToSpendMoney.getBalance().subtract(amount);
        log.info("balance after decrease: " + amountAfterDecrease);
        accountToSpendMoney.setBalance(amountAfterDecrease);
        adapterAccountRepository.save(accountMapper.updateUserAccountEntityFromUserAccount(accountToSpendMoney));
    }

    private Account createMainAccount(final Long userId, final Account account) {
        account.setUserId(userId);
        account.setAccountName("Main account");
        account.setCurrency("PLN");
        account.setNumber(prepareAccountData(account).getNumber());
        adapterAccountRepository.save(accountMapper.mapToUserAccountEntityFromUserAccount(account));
        return account;
    }

    public Account getAccountByAccountId(final Long accountId) {
        Account account = accountMapper.mapToUserAccountFromUserAccountEntity(adapterAccountRepository.findById(accountId));
        log.info("user account " + account.toString());
        return account;
    }
}
