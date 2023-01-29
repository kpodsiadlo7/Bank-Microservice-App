package com.accountsmanager.service.mapper;

import com.accountsmanager.AccountsManagerApplication;
import com.accountsmanager.domain.UserAccountEntity;
import com.accountsmanager.repository.adapter.AdapterUserAccountRepository;
import com.accountsmanager.service.data.UserAccount;
import com.accountsmanager.web.dto.UserAccountDto;
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

import static org.mockito.ArgumentMatchers.anyLong;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountsManagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAccountMapperTestSuite {

    @Autowired
    private UserAccountsMapper userAccountsMapper;
    @Autowired
    private AdapterUserAccountRepository adapterUserAccountRepository;

    @Test
    void mapToUserAccountDtoFromUserAccount() {
        //given
        UserAccount userAccount = new UserAccount(
                null,
                3L,
                "account name",
                new BigDecimal(3000),
                "123",
                "PLN",
                "zł"
        );
        //when
        UserAccountDto accountAfterMapper = userAccountsMapper.mapToUserAccountDtoFromUserAccount(userAccount);
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
        UserAccountDto userAccount = new UserAccountDto(
                null,
                3L,
                "account name",
                new BigDecimal(3000),
                "123",
                "PLN",
                "zł"
        );
        //when
        UserAccount accountAfterMapper = userAccountsMapper.mapToUserAccountFromUserAccountDto(userAccount);
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
        UserAccount userAccount = new UserAccount(
                null,
                3L,
                "account name",
                new BigDecimal(3000),
                "123",
                "PLN",
                "zł"
        );
        //when
        UserAccountEntity accountAfterMapper = userAccountsMapper.mapToUserAccountEntityFromUserAccount(userAccount);
        adapterUserAccountRepository.save(accountAfterMapper);
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
        UserAccount userAccount = new UserAccount(
                null,
                3L,
                "account name",
                new BigDecimal(3000),
                "123",
                "PLN",
                "zł"
        );
        //when
        UserAccountEntity accountEntity = userAccountsMapper.mapToUserAccountEntityFromUserAccount(userAccount);
        adapterUserAccountRepository.save(accountEntity);
        //and
        UserAccount accountAfterMapper = userAccountsMapper.mapToUserAccountFromUserAccountEntity(accountEntity);
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
        List<UserAccountEntity> entityAccountList = new ArrayList<>();
        UserAccount account1ToEntity = new UserAccount(null, 3L, "account name", new BigDecimal(3000), "123", "PLN", "zł");
        UserAccount account2ToEntity = new UserAccount(null, 55L, "account", new BigDecimal(30020), "444", "EUR", "€");
        //and
        UserAccountEntity account1Entity = userAccountsMapper.mapToUserAccountEntityFromUserAccount(account1ToEntity);
        UserAccountEntity account2Entity = userAccountsMapper.mapToUserAccountEntityFromUserAccount(account2ToEntity);
        adapterUserAccountRepository.save(account1Entity);
        adapterUserAccountRepository.save(account2Entity);
        //and
        entityAccountList.add(account1Entity);
        entityAccountList.add(account2Entity);
        //when
        List<UserAccount> accountListAfterMapper = userAccountsMapper.mapToUserAccountListFromUserAccountEntityList(entityAccountList);
        //then
        Assertions.assertEquals(2, accountListAfterMapper.size());

        Assertions.assertNotNull(accountListAfterMapper.stream()
                .filter(f->f.getUserId().equals(3L)).map(UserAccount::getId));

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
        List<UserAccount> userAccountList = new ArrayList<>();
        UserAccount account1 = new UserAccount(null, 3L, "account name", new BigDecimal(3000), "123", "PLN", "zł");
        UserAccount account2 = new UserAccount(null, 7L, "account", new BigDecimal(333000), "221", "EUR", "€");
        //and
        userAccountList.add(account1);
        userAccountList.add(account2);
        //when
        List<UserAccountDto> accountListAfterMapper = userAccountsMapper.mapToUserAccountDtoListFromUserAccountList(userAccountList);
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
