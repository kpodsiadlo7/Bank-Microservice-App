package com.mainapp.service.mapper;

import com.mainapp.MainAppApplication;
import com.mainapp.service.data.UserAccount;
import com.mainapp.web.dto.UserAccountDto;
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
public class UserAccountMapperTestSuite {

    @Autowired
    private UserAccountsMapper accountsMapper;

    @Test
    void mapToUserAccountDtoFromUserAccount(){
        //given
        UserAccount userAccount = new UserAccount(
                7L,
                "account name",
                new BigDecimal(23333),
                "123",
                "PLN",
                "zł"
        );
        //when
        UserAccountDto userAfterMapper = accountsMapper.mapToUserAccountDtoFromUserAccount(userAccount);
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
        UserAccountDto accountDto = new UserAccountDto(
                7L,
                "account name",
                new BigDecimal(23333),
                "123",
                "PLN",
                "zł"
        );
        //when
        UserAccount userAfterMapper = accountsMapper.mapToUserAccountFromUserAccountDto(accountDto);
        //then
        Assertions.assertEquals(7,userAfterMapper.getId());
        Assertions.assertEquals("account name",userAfterMapper.getAccountName());
        Assertions.assertEquals(new BigDecimal(23333),userAfterMapper.getBalance());
        Assertions.assertEquals("123",userAfterMapper.getNumber());
        Assertions.assertEquals("PLN",userAfterMapper.getCurrency());
        Assertions.assertEquals("zł",userAfterMapper.getCurrencySymbol());
    }
}
