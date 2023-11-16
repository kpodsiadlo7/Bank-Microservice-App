package com.mainapp.service.mapper;

import com.mainapp.MainAppApplication;
import com.mainapp.account.dto.AccountDto;
import com.mainapp.security.AuthorityEntity;
import com.mainapp.user.User;
import com.mainapp.user.dto.UserDto;
import com.mainapp.user.UserFacade;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor
@SpringBootTest(classes = MainAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserMapperTestSuite {

    private final UserFacade userFacade;

    @Test
    void mapToUserFromUserDto() {
        //given
        UserDto userDto = new UserDto(
                7L,
                "username",
                "realname",
                "password",
                "confirmpassword",
                Set.of(new AuthorityEntity()),
                Set.of(AccountDto.builder().build())
        );
        //when
        User userAfterMapper = userFacade.mapToUserFromUserDto(userDto);
        //then
        Assertions.assertEquals(1,userAfterMapper.getAuthorities().size());
        Assertions.assertEquals(1,userAfterMapper.getAccounts().size());
        Assertions.assertEquals(7,userAfterMapper.getId());
        Assertions.assertEquals("username",userAfterMapper.getUsername());
        Assertions.assertEquals("realname",userAfterMapper.getRealName());
        Assertions.assertEquals("password",userAfterMapper.getPassword());
        Assertions.assertEquals("confirmpassword",userAfterMapper.getConfirmPassword());
    }

    @Test
    void mapToUserDtoFromUser(){
        //given
        User user = new User(
                7L,
                "username",
                "realname",
                "password",
                "confirmpassword",
                Set.of(new AuthorityEntity()),
                Set.of(AccountDto.builder().build())
        );
        //when
        UserDto userAfterMapper = userFacade.mapToUserDtoFromUser(user);
        //then
        Assertions.assertEquals(1,userAfterMapper.getAuthorities().size());
        Assertions.assertEquals(1,userAfterMapper.getAccounts().size());
        Assertions.assertEquals(7,userAfterMapper.getId());
        Assertions.assertEquals("username",userAfterMapper.getUsername());
        Assertions.assertEquals("realname",userAfterMapper.getRealName());
        Assertions.assertEquals("password",userAfterMapper.getPassword());
        Assertions.assertEquals("confirmpassword",userAfterMapper.getConfirmPassword());
    }
}
