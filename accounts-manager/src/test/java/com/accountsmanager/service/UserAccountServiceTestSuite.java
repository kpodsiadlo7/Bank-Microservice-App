package com.accountsmanager.service;

import com.accountsmanager.AccountsManagerApplication;
import com.accountsmanager.domain.UserAccountEntity;
import com.accountsmanager.repository.adapter.AdapterUserAccountRepository;
import com.accountsmanager.service.data.UserAccount;
import com.accountsmanager.service.mapper.UserAccountsMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountsManagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAccountServiceTestSuite {

    @Autowired
    private AdapterUserAccountRepository adapterUserAccountRepository;
    @Autowired
    private UserAccountsMapper userAccountsMapper;

    @Test
    @DisplayName("should create main account using validateData() when we passing user account without currency")
    void createMainAccountPassingUserAccountWithoutCurrency() {
        //given
        UserAccount userAccount = new UserAccount();
        //and
        var mockRepository = mock(UserAccountsMapper.class);
        //and
        var adapterUserRepo = mock(AdapterUserAccountRepository.class);
        //system under test
        var toTest = new UserAccountService(mockRepository, adapterUserRepo);
        //when
        UserAccount userAccountAfterTest = toTest.validateData(7L, userAccount);
        //then
        Assertions.assertEquals(7, userAccountAfterTest.getUserId());
        Assertions.assertEquals("Main account", userAccountAfterTest.getAccountName());
        Assertions.assertEquals(new BigDecimal(0), userAccountAfterTest.getBalance());
        Assertions.assertEquals("PL55", userAccountAfterTest.getNumber().substring(0, 4));
        Assertions.assertEquals("zł", userAccountAfterTest.getCurrencySymbol());
    }

    @Test
    @DisplayName("should create user EUR account using validateData() when we pass correct data")
    void createAccountForUserPassingCorrectData() {
        //given
        UserAccount userAccount = new UserAccount(
                null,
                null,
                "student account",
                new BigDecimal(0),
                null,
                "EUR",
                "€"
        );
        //system under test
        var toTest = new UserAccountService(userAccountsMapper, adapterUserAccountRepository);
        //when
        UserAccount userAccountAfterTest = toTest.validateData(7L, userAccount);
        //then
        Assertions.assertNotNull(userAccountAfterTest.getId());
        Assertions.assertEquals(7, userAccountAfterTest.getUserId());
        Assertions.assertEquals("student account", userAccountAfterTest.getAccountName());
        Assertions.assertEquals(new BigDecimal(0), userAccountAfterTest.getBalance());
        Assertions.assertEquals("DE49", userAccountAfterTest.getNumber().substring(0, 4));
        Assertions.assertEquals("EUR", userAccountAfterTest.getCurrency());
        Assertions.assertEquals("€", userAccountAfterTest.getCurrencySymbol());
    }

    @Test
    @DisplayName("should return real data from database when we saving several examples one in EUR one in PLN")
    void getAllUserAccounts() {
        //given
        UserAccountEntity userAccountPLN = new UserAccountEntity(7L, "pln account", new BigDecimal(777), "PL55123123", "PLN", "zł");
        UserAccountEntity userAccountEUR = new UserAccountEntity(7L, "euro account", new BigDecimal(400), "DE49123123", "EUR", "€");
        userAccountPLN.setId(111L);
        userAccountEUR.setId(333L);
        adapterUserAccountRepository.save(userAccountPLN);
        adapterUserAccountRepository.save(userAccountEUR);
        //and
        //system under test
        var toTest = new UserAccountService(userAccountsMapper, adapterUserAccountRepository);
        //when
        List<UserAccount> accountAfterRealMethod = toTest.getAllUserAccounts(7L);
        //then
        Assertions.assertEquals(2, accountAfterRealMethod.size());
        Assertions.assertTrue(accountAfterRealMethod.stream()
                .anyMatch(currency -> currency.getCurrency().equals("PLN")));
        Assertions.assertTrue(accountAfterRealMethod.stream()
                .anyMatch(currency -> currency.getCurrency().equals("EUR")));
    }

    @Test
    @DisplayName("exists by number should return true when we check if account number which is 'generated' exist in database before assign to the user")
    void existsByNumberInDb() {
        //given
        UserAccountEntity userAccountPLN = new UserAccountEntity(7L, "pln account", new BigDecimal(777), "PL55123123", "PLN", "zł");
        adapterUserAccountRepository.save(userAccountPLN);
        //when
        boolean thisShouldBeTrue = adapterUserAccountRepository.existsByNumber("PL55123123");
        //then
        Assertions.assertTrue(thisShouldBeTrue);
    }
}
