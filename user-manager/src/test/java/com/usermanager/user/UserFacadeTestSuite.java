package com.usermanager.user;

import com.usermanager.UserManagerApplication;
import com.usermanager.user.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserManagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserFacadeTestSuite {

    @Autowired
    private UserFactory userFactory;
    @Autowired
    private AdapterUserEntityRepository adapterUserEntityRepository;

    @Test
    void testValidateDataWithoutPassword() {
        //given
        User user = new User(null, "userek", "real name", null, "confirmpassword");
        //system under test
        var toTest = new UserFacade(null, null);
        //when
        String shouldBeFillPassword = toTest.validateData(user).getUsername();
        //then
        Assertions.assertEquals("Fill password fields", shouldBeFillPassword);
    }

    @Test
    void testValidateDataWithoutRealName() {
        //given
        User user = new User(null, "userek", null, "password", "confirmpassword");
        //system under test
        var toTest = new UserFacade(null, null);
        //when
        String shouldBeEnterName = toTest.validateData(user).getUsername();
        //then
        Assertions.assertEquals("Enter name", shouldBeEnterName);
    }

    @Test
    void testValidateDataWithoutUsername() {
        //given
        User user = new User(null, null, "real name", "password", "confirmpassword");
        //system under test
        var toTest = new UserFacade(null, null);
        //when
        String shouldBeEnterLogin = toTest.validateData(user).getUsername();
        //then
        Assertions.assertEquals("Enter login", shouldBeEnterLogin);
    }

    @Test
    void testValidateDataUserAlreadyExistInDb() {
        //given
        User user = new User(null, "username", "real name", "password", "confirmpassword");
        var mockRepo = mock(AdapterUserEntityRepository.class);
        when(mockRepo.existsByUsername(anyString())).thenReturn(true);
        //system under test
        var toTest = new UserFacade(mockRepo, null);
        //when
        String shouldBeUserExist = toTest.validateData(user).getUsername();
        //then
        Assertions.assertEquals("User already exist!", shouldBeUserExist);
    }

    @Test
    @DisplayName("should register user in database when we pass correct data")
    void registerUser() {
        //given
        User user = new User(null, "username", "real name", "password", "confirm password");
        var mockRepo = mock(AdapterUserEntityRepository.class);
        when(mockRepo.existsByUsername(anyString())).thenReturn(false);
        //system under test
        var toTest = new UserFacade(mockRepo, userFactory);
        //when
        UserDto userAfterTest = toTest.validateData(user);
        //then
        Assertions.assertEquals("username", userAfterTest.getUsername());
        Assertions.assertEquals("real name", userAfterTest.getRealName());
        Assertions.assertEquals("password", userAfterTest.getPassword());
    }
    @Test
    void loginUser(){
        //given
        User user = new User(null, "username", "real name", "password", "confirm password");
        UserEntity userEntity = userFactory.mapToUserEntityFromUser(user);
        adapterUserEntityRepository.save(userEntity);
        //system under test
        var toTest = new UserFacade(adapterUserEntityRepository, userFactory);
        //when
        UserDto userAfterTest = toTest.loginUser("username");
        //then
        Assertions.assertNotNull(userAfterTest.getId());
        Assertions.assertEquals("username",userAfterTest.getUsername());
        Assertions.assertEquals("real name",userAfterTest.getRealName());
        Assertions.assertEquals("password",userAfterTest.getPassword());
    }
}
