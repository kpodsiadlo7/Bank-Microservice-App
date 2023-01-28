package com.usermanager.service.mapper;

import com.usermanager.UserManagerApplication;
import com.usermanager.domain.UserEntity;
import com.usermanager.repository.adapter.AdapterUserEntityRepository;
import com.usermanager.service.data.User;
import com.usermanager.web.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserManagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTestSuite {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdapterUserEntityRepository adapterUserEntityRepository;

    @Test
    void mapToUserDtoFromUser(){
        //given
        User user = new User(
                7L,
                "user",
                "real user",
                "password",
                "plain password"
        );
        //when
        UserDto userAfterMapper = userMapper.mapToUserDtoFromUser(user);
        //then
        Assertions.assertEquals(7L,userAfterMapper.getId());
        Assertions.assertEquals("real name",userAfterMapper.getRealName());
        Assertions.assertEquals("password",userAfterMapper.getPassword());
        Assertions.assertEquals("plain password",userAfterMapper.getPlainPassword());
    }

    @Test
    void mapToUserFromUserDto(){
        //given
        UserDto userDto = new UserDto(
                7L,
                "user dto",
                "real dto",
                "dtopass",
                "plain dtopass"
        );
        //when
        User userAfterMapper = userMapper.mapToUserFromUserDto(userDto);
        //then
        Assertions.assertEquals(7L,userAfterMapper.getId());
        Assertions.assertEquals("real dto",userAfterMapper.getRealName());
        Assertions.assertEquals("dtopass",userAfterMapper.getPassword());
        Assertions.assertEquals("plain dtopass",userAfterMapper.getPlainPassword());
    }

    @Test
    void mapToUserEntityFromUser(){
        //given
        User user = new User(
                7L,
                "user",
                "user",
                "user",
                "plain user"
        );
        //when
        UserEntity userAfterMapper = userMapper.mapToUserEntityFromUser(user);
        adapterUserEntityRepository.save(userAfterMapper);
        Long userId = userAfterMapper.getId();
        //then
        Assertions.assertNotEquals(7, userAfterMapper.getId());
        Assertions.assertEquals(1,userId);
        Assertions.assertEquals("user",userAfterMapper.getRealName());
        Assertions.assertEquals("user",userAfterMapper.getPassword());
        Assertions.assertEquals("plain user",userAfterMapper.getPlainPassword());
    }

    @Test
    void mapToUserFromUserEntity(){
        //given
        UserEntity userEntity = new UserEntity(
                7L,
                "entity",
                "entity",
                "entity",
                "plain entity"
        );
        //when
        User userAfterMapper = userMapper.mapToUserFromUserEntity(userEntity);
        //then
        Assertions.assertEquals(7,userAfterMapper.getId());
        Assertions.assertEquals("entity",userAfterMapper.getRealName());
        Assertions.assertEquals("entity",userAfterMapper.getPassword());
        Assertions.assertEquals("plain entity",userAfterMapper.getPlainPassword());
    }
}
