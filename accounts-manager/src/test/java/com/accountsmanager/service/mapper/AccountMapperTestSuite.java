package com.accountsmanager.service.mapper;

import com.accountsmanager.AccountsManagerApplication;
import com.accountsmanager.domain.AccountEntity;
import com.accountsmanager.repository.adapter.AdapterAccountRepository;
import com.accountsmanager.service.data.Account;
import com.accountsmanager.web.dto.AccountDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountsManagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountMapperTestSuite {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AdapterAccountRepository adapterAccountRepository;

    @Test
    void mapToUserAccountDtoFromUserAccount() {
        //given
        Account account = new Account(
                null,
                3L,
                "account name",
                new BigDecimal(3000),
                "123",
                "PLN",
                "zł"
        );
        //when
        AccountDto accountAfterMapper = accountMapper.mapToUserAccountDtoFromUserAccount(account);
        //then
        Assertions.assertEquals(3, accountAfterMapper.getUserId());
        Assertions.assertNull(accountAfterMapper.getId());
        Assertions.assertEquals("account name", accountAfterMapper.getAccountName());
        Assertions.assertEquals(new BigDecimal(3000), accountAfterMapper.getBalance());
        Assertions.assertEquals("123", accountAfterMapper.getNumber());
        Assertions.assertEquals("PLN", accountAfterMapper.getCurrency());
        Assertions.assertEquals("zł", accountAfterMapper.getCurrencySymbol());
    }

    @Test
    void mapToUserAccountFromUserAccountDto() {
        //given
        AccountDto userAccount = new AccountDto(
                null,
                3L,
                "account name",
                new BigDecimal(3000),
                "123",
                "PLN",
                "zł"
        );
        //when
        Account accountAfterMapper = accountMapper.mapToUserAccountFromUserAccountDto(userAccount);
        //then
        Assertions.assertNull(accountAfterMapper.getId());
        Assertions.assertEquals(3, accountAfterMapper.getUserId());
        Assertions.assertEquals("account name", accountAfterMapper.getAccountName());
        Assertions.assertEquals(new BigDecimal(3000), accountAfterMapper.getBalance());
        Assertions.assertEquals("123", accountAfterMapper.getNumber());
        Assertions.assertEquals("PLN", accountAfterMapper.getCurrency());
        Assertions.assertEquals("zł", accountAfterMapper.getCurrencySymbol());
    }

    @Test
    void mapToUserAccountEntityFromUserAccountAndSaveToDb() {
        //given
        Account account = new Account(
                null,
                3L,
                "account name",
                new BigDecimal(3000),
                "123",
                "PLN",
                "zł"
        );
        //when
        AccountEntity accountAfterMapper = accountMapper.mapToUserAccountEntityFromUserAccount(account);
        adapterAccountRepository.save(accountAfterMapper);
        //then
        Assertions.assertNotEquals(7, accountAfterMapper.getId());
        Assertions.assertNotNull(accountAfterMapper.getId());
        Assertions.assertEquals(3, accountAfterMapper.getUserId());
        Assertions.assertEquals("account name", accountAfterMapper.getAccountName());
        Assertions.assertEquals(new BigDecimal(3000), accountAfterMapper.getBalance());
        Assertions.assertEquals("123", accountAfterMapper.getNumber());
        Assertions.assertEquals("PLN", accountAfterMapper.getCurrency());
        Assertions.assertEquals("zł", accountAfterMapper.getCurrencySymbol());
    }

    @Test
    void mapToUserAccountFromUserAccountEntityAndSaveToDb() {
        //given
        Account account = new Account(
                null,
                3L,
                "account name",
                new BigDecimal(3000),
                "123",
                "PLN",
                "zł"
        );
        //when
        AccountEntity accountEntity = accountMapper.mapToUserAccountEntityFromUserAccount(account);
        adapterAccountRepository.save(accountEntity);
        //and
        Account accountAfterMapper = accountMapper.mapToUserAccountFromUserAccountEntity(accountEntity);
        //then
        Assertions.assertNotEquals(7, accountAfterMapper.getId());
        Assertions.assertNotNull(accountAfterMapper.getId());
        Assertions.assertEquals(3, accountAfterMapper.getUserId());
        Assertions.assertEquals("account name", accountAfterMapper.getAccountName());
        Assertions.assertEquals(new BigDecimal(3000), accountAfterMapper.getBalance());
        Assertions.assertEquals("123", accountAfterMapper.getNumber());
        Assertions.assertEquals("PLN", accountAfterMapper.getCurrency());
        Assertions.assertEquals("zł", accountAfterMapper.getCurrencySymbol());
    }

    @Test
    void mapToUserAccountListFromUserAccountEntityListAndSaveToDb() {
        //given
        List<AccountEntity> entityAccountList = new ArrayList<>();
        Account account1ToEntity = new Account(null, 3L, "account name", new BigDecimal(3000), "123", "PLN", "zł");
        Account account2ToEntity = new Account(null, 55L, "account", new BigDecimal(30020), "444", "EUR", "€");
        //and
        AccountEntity account1Entity = accountMapper.mapToUserAccountEntityFromUserAccount(account1ToEntity);
        AccountEntity account2Entity = accountMapper.mapToUserAccountEntityFromUserAccount(account2ToEntity);
        adapterAccountRepository.save(account1Entity);
        adapterAccountRepository.save(account2Entity);
        //and
        entityAccountList.add(account1Entity);
        entityAccountList.add(account2Entity);
        //when
        List<Account> accountListAfterMapper = accountMapper.mapToUserAccountListFromUserAccountEntityList(entityAccountList);
        //then
        Assertions.assertEquals(2, accountListAfterMapper.size());

        Assertions.assertNotNull(accountListAfterMapper.stream()
                .filter(f->f.getUserId().equals(3L)).map(Account::getId));

        Assertions.assertTrue(accountListAfterMapper.stream()
                .anyMatch(userId -> userId.getUserId().equals(3L)));

        Assertions.assertTrue(accountListAfterMapper.stream()
                .anyMatch(name -> name.getAccountName().equals("account")));

        Assertions.assertTrue(accountListAfterMapper.stream()
                .anyMatch(balance -> balance.getBalance().equals(new BigDecimal(3000))));

        Assertions.assertTrue(accountListAfterMapper.stream()
                .anyMatch(num -> num.getNumber().equals("123")));

        Assertions.assertTrue(accountListAfterMapper.stream()
                .anyMatch(cur -> cur.getCurrency().equals("EUR")));

        Assertions.assertTrue(accountListAfterMapper.stream()
                .anyMatch(symbol -> symbol.getCurrencySymbol().equals("€")));
    }

    @Test
    void mapToUserAccountDtoListFromUserAccountList() {
        //given
        List<Account> accountList = new ArrayList<>();
        Account account1 = new Account(null, 3L, "account name", new BigDecimal(3000), "123", "PLN", "zł");
        Account account2 = new Account(null, 7L, "account", new BigDecimal(333000), "221", "EUR", "€");
        //and
        accountList.add(account1);
        accountList.add(account2);
        //when
        List<AccountDto> accountListAfterMapper = accountMapper.mapToUserAccountDtoListFromUserAccountList(accountList);
        //then
        Assertions.assertEquals(2, accountListAfterMapper.size());

        Assertions.assertTrue(accountListAfterMapper.stream()
                .anyMatch(userId -> userId.getUserId().equals(3L)));

        Assertions.assertTrue(accountListAfterMapper.stream()
                .anyMatch(name -> name.getAccountName().equals("account")));

        Assertions.assertTrue(accountListAfterMapper.stream()
                .anyMatch(balance -> balance.getBalance().equals(new BigDecimal(333000))));

        Assertions.assertTrue(accountListAfterMapper.stream()
                .anyMatch(num -> num.getNumber().equals("221")));

        Assertions.assertTrue(accountListAfterMapper.stream()
                .anyMatch(cur -> cur.getCurrency().equals("PLN")));

        Assertions.assertTrue(accountListAfterMapper.stream()
                .anyMatch(symbol -> symbol.getCurrencySymbol().equals("zł")));

    }
}
