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
        log.info("validate data");
        if (account.getCurrency() == null)
            return createMainAccount(userId, account);
        return createAccountForUser(userId, account);
    }

    public Account getAccountByAccountId(final Long accountId) {
        log.info("get account by id");
        return accountMapper.mapToUserAccountFromUserAccountEntity(adapterAccountRepository.findById(accountId));
    }

    private Account createAccountForUser(final Long userId, final Account account) {
        log.info("create account for user");
        account.setUserId(userId);
        account.setNumber(prepareAccountData(account).getNumber());
        AccountEntity accountEntity = accountMapper.mapToUserAccountEntityFromUserAccount(account);
        adapterAccountRepository.save(accountEntity);
        return accountMapper.mapToUserAccountFromUserAccountEntity(accountEntity);
    }


    private Account prepareAccountData(final Account account) {
        log.info("prepare account data");
        //start money
        account.setBalance(new BigDecimal(10000));
        return createNumberForAccount(account);
    }

    private Account createNumberForAccount(final Account account) {
        log.info("create number for account");
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
        log.info("deposit money before validation");
        if (!Objects.equals(validateDataBeforeTransaction(thisAccountId, transfer).getAmount(), new BigDecimal(-1)))
            increaseMoney(thisAccountId, transfer.getAmount());
        log.info("deposit money after validation");
        return transfer;
    }

    public Transfer withdrawMoney(final Long thisAccountId, final Transfer transfer) {
        log.info("withdraw money");
        if (!Objects.equals(validateDataBeforeTransaction(thisAccountId, transfer).getAmount(), new BigDecimal(-1)))
            decreaseMoney(thisAccountId, transfer.getAmount());
        return transfer;
    }

    public List<Account> getAllUserAccounts(final Long userId) {
        log.info("get all user accounts");
        List<AccountEntity> accountEntities = adapterAccountRepository.findAllByUserId(userId);
        return accountMapper.mapToUserAccountListFromUserAccountEntityList(accountEntities);
    }

    public Transfer moneyTransferFromUserToUser(final Long thisAccountId, final Transfer transfer) {
        log.info("money transfer from user to user");
        if (!Objects.equals(validateDataBeforeTransaction(thisAccountId, transfer).getAmount(), new BigDecimal(-1)))
            return closeMoneyTransfer(thisAccountId, transfer);
        return transfer;
    }

    public Transfer validateDataBeforeTransaction(final Long thisAccountId, final Transfer transfer) {
        //this case is for taking credit
        if (transfer.getAccountNumber().equals("credit") || transfer.getAccountNumber().equals("commission"))
            return transfer;


        if (transfer.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            log.info("amount min 0");
            transfer.setAccountNumber("Minimum amount must be over 0");
            transfer.setAmount(new BigDecimal(-1));
            return transfer;
        }

        if (adapterAccountRepository.findById(thisAccountId).getBalance().compareTo(transfer.getAmount()) < 0) {
            if (transfer.getAccountNumber().equals("deposit")) {
                log.info("deposit");
                return transfer;
            }
            transfer.setAmount(new BigDecimal(-1));
            log.info("dont have enough money");
            transfer.setAccountNumber("You don't have enough money");
            return transfer;
        }


        if (adapterAccountRepository.findById(thisAccountId).getNumber().equals(transfer.getAccountNumber())) {
            transfer.setAmount(new BigDecimal(-1));
            log.info("him self");
            transfer.setAccountNumber("You can't send money to account which from you send");
            return transfer;
        }
        if (!adapterAccountRepository.existsByNumber(transfer.getAccountNumber())) {
            if (transfer.getAccountNumber().equals("deposit") ||
                    transfer.getAccountNumber().equals("withdraw")) {
                log.info("deposit or withdraw");
                return transfer;
            }
            log.info("user doesnt exist");
            transfer.setAmount(new BigDecimal(-1));
            transfer.setAccountNumber("User with this number doesn't exist!");
            return transfer;
        }
        log.info("end of validation");
        return transfer;
    }

    @Transactional
    protected Transfer closeMoneyTransfer(final Long thisAccountId, final Transfer transfer) {
        log.info("close money transfer");
        Long accountIncreaseId = adapterAccountRepository.findByNumber(transfer.getAccountNumber()).getId();
        increaseMoney(accountIncreaseId, transfer.getAmount());
        decreaseMoney(thisAccountId, transfer.getAmount());

        transfer.setAccountReceiveId(accountIncreaseId);
        return transfer;
    }

    protected void increaseMoney(final Long thisAccountId, final BigDecimal amount) {
        log.info("increase money " +amount);
        Account accountToReceiveMoney = accountMapper.mapToUserAccountFromUserAccountEntity
                (adapterAccountRepository.findById(thisAccountId));
        BigDecimal amountAfterIncrease = accountToReceiveMoney.getBalance().add(amount);
        accountToReceiveMoney.setBalance(amountAfterIncrease);
        log.info("save account after increase money");
        adapterAccountRepository.save(accountMapper.updateUserAccountEntityFromUserAccount(accountToReceiveMoney));
    }

    protected void decreaseMoney(final Long thisAccountId, final BigDecimal amount) {
        log.info("decrease money" +amount);
        Account accountToSpendMoney = accountMapper.mapToUserAccountFromUserAccountEntity
                (adapterAccountRepository.findById(thisAccountId));
        BigDecimal amountAfterDecrease = accountToSpendMoney.getBalance().subtract(amount);
        accountToSpendMoney.setBalance(amountAfterDecrease);
        log.info("save account after decrease money");
        adapterAccountRepository.save(accountMapper.updateUserAccountEntityFromUserAccount(accountToSpendMoney));
    }

    private Account createMainAccount(final Long userId, final Account account) {
        log.info("create main account");
        account.setUserId(userId);
        account.setAccountName("Main account");
        account.setCurrency("PLN");
        account.setNumber(prepareAccountData(account).getNumber());
        adapterAccountRepository.save(accountMapper.mapToUserAccountEntityFromUserAccount(account));
        return account;
    }

}
