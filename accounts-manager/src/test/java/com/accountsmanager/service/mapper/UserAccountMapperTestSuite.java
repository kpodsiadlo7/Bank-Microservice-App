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

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountsManagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAccountMapperTestSuite {

    @Autowired
    private UserAccountsMapper userAccountsMapper;
    @Autowired
    private AdapterUserAccountRepository adapterUserAccountRepository;

    @Test
    void mapToUserAccountDtoFromUserAccount(){
        //given
        UserAccount userAccount = new UserAccount(
                7L,
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
        Assertions.assertEquals(7,accountAfterMapper.getId());
        Assertions.assertEquals(3,accountAfterMapper.getUserId());
        Assertions.assertEquals("account name",accountAfterMapper.getAccountName());
        Assertions.assertEquals(new BigDecimal(3000),accountAfterMapper.getBalance());
        Assertions.assertEquals("123",accountAfterMapper.getNumber());
        Assertions.assertEquals("PLN",accountAfterMapper.getCurrency());
        Assertions.assertEquals("zł",accountAfterMapper.getCurrencySymbol());
    }

    @Test
    void mapToUserAccountFromUserAccountDto(){
        //given
        UserAccountDto userAccount = new UserAccountDto(
                7L,
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
        Assertions.assertEquals(7,accountAfterMapper.getId());
        Assertions.assertEquals(3,accountAfterMapper.getUserId());
        Assertions.assertEquals("account name",accountAfterMapper.getAccountName());
        Assertions.assertEquals(new BigDecimal(3000),accountAfterMapper.getBalance());
        Assertions.assertEquals("123",accountAfterMapper.getNumber());
        Assertions.assertEquals("PLN",accountAfterMapper.getCurrency());
        Assertions.assertEquals("zł",accountAfterMapper.getCurrencySymbol());
    }

    @Test
    void mapToUserAccountEntityFromUserAccount(){
        //given
        UserAccount userAccount = new UserAccount(
                7L,
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
        Assertions.assertNotEquals(7,accountAfterMapper.getId());
        Assertions.assertEquals(2,accountAfterMapper.getId());
        Assertions.assertEquals(3,accountAfterMapper.getUserId());
        Assertions.assertEquals("account name",accountAfterMapper.getAccountName());
        Assertions.assertEquals(new BigDecimal(3000),accountAfterMapper.getBalance());
        Assertions.assertEquals("123",accountAfterMapper.getNumber());
        Assertions.assertEquals("PLN",accountAfterMapper.getCurrency());
        Assertions.assertEquals("zł",accountAfterMapper.getCurrencySymbol());
    }

    @Test
    void mapToUserAccountFromUserAccountEntityAndSaveToDb(){
        //given
        UserAccount userAccount = new UserAccount(
                7L,
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
        Assertions.assertNotEquals(7,accountAfterMapper.getId());
        Assertions.assertEquals(1,accountAfterMapper.getId());
        Assertions.assertEquals(3,accountAfterMapper.getUserId());
        Assertions.assertEquals("account name",accountAfterMapper.getAccountName());
        Assertions.assertEquals(new BigDecimal(3000),accountAfterMapper.getBalance());
        Assertions.assertEquals("123",accountAfterMapper.getNumber());
        Assertions.assertEquals("PLN",accountAfterMapper.getCurrency());
        Assertions.assertEquals("zł",accountAfterMapper.getCurrencySymbol());
    }
}
