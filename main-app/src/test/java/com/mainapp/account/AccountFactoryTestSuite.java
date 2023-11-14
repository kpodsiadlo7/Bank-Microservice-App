package com.mainapp.account;

import com.mainapp.MainAppApplication;
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
@SpringBootTest(classes = MainAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountFactoryTestSuite {

    @Autowired
    private AccountFactory accountsMapper;

    @Test
    void mapToUserAccountDtoFromUserAccount(){
        //given
        Account account = new Account(
                7L,
                6L,
                "account name",
                new BigDecimal(23333),
                new BigDecimal(0),
                "123",
                "PLN",
                "zł"
        );
        //when
        AccountDto userAfterMapper = accountsMapper.buildAccountDtoFromAccount(account);
        //then
        Assertions.assertEquals(7,userAfterMapper.getId());
        Assertions.assertEquals("account name",userAfterMapper.getAccountName());
        Assertions.assertEquals(new BigDecimal(23333),userAfterMapper.getBalance());
        Assertions.assertEquals("123",userAfterMapper.getNumber());
        Assertions.assertEquals("PLN",userAfterMapper.getCurrency());
        Assertions.assertEquals("zł",userAfterMapper.getCurrencySymbol());
    }

    @Test
    void mapToUserAccountFromUserAccountDto(){
        //given
        AccountDto accountDto = new AccountDto(
                7L,
                6L,
                "account name",
                new BigDecimal(23333),
                new BigDecimal(0),
                "123",
                "PLN",
                "zł"
        );
        //when
        Account userAfterMapper = accountsMapper.fromAccountDtoToAccount(accountDto);
        //then
        Assertions.assertEquals(7,userAfterMapper.getId());
        Assertions.assertEquals("account name",userAfterMapper.getAccountName());
        Assertions.assertEquals(new BigDecimal(23333),userAfterMapper.getBalance());
        Assertions.assertEquals("123",userAfterMapper.getNumber());
        Assertions.assertEquals("PLN",userAfterMapper.getCurrency());
        Assertions.assertEquals("zł",userAfterMapper.getCurrencySymbol());
    }
}
