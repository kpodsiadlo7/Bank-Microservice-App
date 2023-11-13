package com.accountsmanager.account;

import com.accountsmanager.account.dto.AccountDto;
import com.accountsmanager.currencyapi.CurrencyFacade;
import com.accountsmanager.transfer.TransferDto;
import com.accountsmanager.transfer.TransferFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountFacade {

    private final AccountMapper accountMapper;
    private final AccountFactory accountFactory;
    private final TransferFacade transferFacade;
    private final CurrencyFacade currencyFacade;
    private final AdapterAccountRepository adapterAccountRepository;

    Account validateData(final Long userId, final Account account) {
        log.info("validate data");
        if (account.getCurrency() == null)
            return createMainAccount(userId, account);
        if (checkIfAccountWithThatCurrencyExist(userId,account.getCurrency())){
            account.setCurrency("exist");
            return account;
        }
        return createAccountForUser(userId, account);
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
    private Account createMainAccount(final Long userId, final Account account) {
        log.info("create main account");
        account.setUserId(userId);
        account.setAccountName("Main account");
        account.setCurrency("PLN");
        account.setNumber(prepareAccountData(account).getNumber());
        adapterAccountRepository.save(accountMapper.mapToUserAccountEntityFromUserAccount(account));
        return account;
    }
    private Account getAccountById(final Long accountId) {
        return accountMapper.mapToUserAccountFromUserAccountEntity
                (adapterAccountRepository.findById(accountId));
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
        if (existByNumber(b.toString())) {
            b.setLength(0);
            return createNumberForAccount(account);
        }
        account.setNumber(b.toString());
        return account;
    }
    List<Account> getAllUserAccounts(final Long userId) {
        log.info("get all user accounts");
        List<AccountEntity> accountEntities = adapterAccountRepository.findAllByUserId(userId);
        return accountMapper.mapToUserAccountListFromUserAccountEntityList(accountEntities);
    }
    AccountDto getAccountByAccountId(final Long accountId) {
        return accountFactory.buildUserAccountDtoFromUserAccount(getAccountById(accountId));
    }
    AccountDto createAccount(Long userId, AccountDto accountDto) {
        return accountFactory.buildUserAccountDtoFromUserAccount(validateData
                (userId, accountMapper.mapToUserAccountFromUserAccountDto(accountDto)));
    }
    List<AccountDto> getAllUserAccountByUserId(Long userId) {
        return accountMapper.mapToUserAccountDtoListFromUserAccountList
                (getAllUserAccounts(userId));
    }
    TransferDto validateDataBeforeTransaction(Long thisAccountId, final TransferDto transferDto){
        if (transferDto.getAccountNumber().equals("credit") || transferDto.getAccountNumber().equals("commission"))
            return transferDto;
        if (getAccountById(thisAccountId).getBalance().compareTo(transferDto.getAmount()) < 0) {
            if (transferDto.getAccountNumber().equals("deposit")) {
                log.info("deposit");
                return transferDto;
            }
            log.info("dont have enough money");
            return transferDto.toBuilder()
                    .withAmount(new BigDecimal(-1))
                    .withAccountNumber("You don't have enough money").build();
        }

        if (getAccountById(thisAccountId).getNumber().equals(transferDto.getAccountNumber())) {
            log.info("him self");
            return transferDto.toBuilder()
                    .withAmount(new BigDecimal(-1))
                    .withAccountNumber("You can't send money to account which from you send").build();
        }
        if (!existByNumber(transferDto.getAccountNumber())) {
            if (transferDto.getAccountNumber().equals("deposit") ||
                    transferDto.getAccountNumber().equals("withdraw")) {
                log.info("deposit or withdraw");
                return transferDto;
            }
            log.info("user doesnt exist");
            log.info("nr account: "+transferDto.getAccountNumber());
            return transferDto.toBuilder()
                    .withAmount(new BigDecimal(-1))
                    .withAccountNumber("User with this number doesn't exist!").build();
        }
        return transferDto;
    }
    TransferDto depositMoney(final Long thisAccountId, final TransferDto transferDto){
        TransferDto error = transferFacade.depositMoney(transferDto);
        if(error.getAmount().equals(new BigDecimal(-1))){
            return error;
        }
        error = validateDataBeforeTransaction(thisAccountId,transferDto);
        if(error.getAmount().equals(new BigDecimal(-1))){
            return error;
        }
        increaseMoney(accountMapper.mapToUserAccountFromUserAccountEntity(
                (adapterAccountRepository.findById(thisAccountId))), transferDto.getAmount());
        return transferDto;
    }
    TransferDto withdrawMoney(final Long thisAccountId, final TransferDto transferDto) {
        TransferDto error = transferFacade.withdrawMoney(transferDto);
        if(error.getAmount().equals(new BigDecimal(-1))){
            return error;
        }
        error = validateDataBeforeTransaction(thisAccountId,transferDto);
        if(error.getAmount().equals(new BigDecimal(-1)))
        {
            return error;
        }
        decreaseMoney(accountMapper.mapToUserAccountFromUserAccountEntity(
                (adapterAccountRepository.findById(thisAccountId))),transferDto.getAmount());
        return transferDto;
    }
    TransferDto moneyTransferFromUserToUser(final Long thisAccountId, final TransferDto transferDto) {
        TransferDto error = validateDataBeforeTransaction(thisAccountId,transferDto);
        if(error.getAmount().equals(new BigDecimal(-1))){
            return error;
        }
        Account accountIncrease = accountMapper.mapToUserAccountFromUserAccountEntity
                (adapterAccountRepository.findByNumber(transferDto.getAccountNumber()));
        Account accountToDecrease = getAccountById(thisAccountId);
        //checking difference currency before increase money in external API
        BigDecimal newAmount = currencyFacade.exchangeCurrency(
                accountIncrease.getCurrency().toLowerCase(),
                accountToDecrease.getCurrency().toLowerCase(), transferDto.getAmount());

        decreaseMoney(accountToDecrease, transferDto.getAmount());
        increaseMoney(accountIncrease, newAmount);
        return transferFacade.moneyTransferFromUserToUser(accountIncrease.getId(),
                        accountIncrease.getUserId(),transferDto.toBuilder().withAmount(newAmount).build());
    }
    boolean checkIfAccountWithThatCurrencyExist(final Long userId, final String currency) {
        return adapterAccountRepository.existsByUserIdAndCurrency(userId,currency);
    }
    private boolean existByNumber(final String accountNumber) {
        return adapterAccountRepository.existsByNumber(accountNumber);
    }
    void setCommitmentsToAccount(final Long accountId, final double monthlyFee) {
        Account account = accountMapper.mapToUserAccountFromUserAccountEntity
                (adapterAccountRepository.findById(accountId));
        account.addCommitments(BigDecimal.valueOf(monthlyFee));
        adapterAccountRepository.save(accountMapper.updateUserAccountEntityFromUserAccount(account));
    }
    private void increaseMoney(final Account accountToIncreaseMoney, final BigDecimal amount) {
        log.info("increase money " + amount);
        BigDecimal amountAfterIncrease = accountToIncreaseMoney.getBalance().add(amount);
        accountToIncreaseMoney.setBalance(amountAfterIncrease);
        adapterAccountRepository.save(accountMapper.updateUserAccountEntityFromUserAccount(accountToIncreaseMoney));
    }

    private void decreaseMoney(final Account accountToDecreaseMoney, final BigDecimal amount) {
        log.info("decrease money" + amount);
        BigDecimal amountAfterDecrease = accountToDecreaseMoney.getBalance().subtract(amount);
        accountToDecreaseMoney.setBalance(amountAfterDecrease);
        adapterAccountRepository.save(accountMapper.updateUserAccountEntityFromUserAccount(accountToDecreaseMoney));
    }
}
