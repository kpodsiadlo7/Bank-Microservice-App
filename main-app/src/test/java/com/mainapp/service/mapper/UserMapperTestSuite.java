package com.mainapp.service.mapper;

import com.mainapp.MainAppApplication;
import com.mainapp.security.AuthorityEntity;
import com.mainapp.user.User;
import com.mainapp.account.Account;
import com.mainapp.user.UserDto;
import com.mainapp.user.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MainAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserMapperTestSuite {

    @Autowired
    private UserMapper userMapper;

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
                Set.of(new Account())
        );
        //when
        User userAfterMapper = userMapper.mapToUserFromUserDto(userDto);
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
                Set.of(new Account())
        );
        //when
        UserDto userAfterMapper = userMapper.mapToUserDtoFromUser(user);
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
