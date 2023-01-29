package com.mainapp.service;

import com.mainapp.MainAppApplication;
import com.mainapp.repository.adapter.AdapterAuthorityRepository;
import com.mainapp.security.AuthorityEntity;
import com.mainapp.service.data.User;
import com.mainapp.service.mapper.UserMapper;
import com.mainapp.web.feign.FeignServiceUserManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MainAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MainAppServiceTestSuite {

    @Test
    @DisplayName("should return \"Password doesn't match!\" when we pass incorrect password with confirm password")
    void testingCreateUserMethodFirstIfWhenWePassNotMatchingPassword() {
        //given
        User user = new User();
        user.setPassword("password");
        user.setConfirmPassword("notpassword");
        //system under test
        var toTest = new MainService(null, null, null, null, null, null);
        //when
        boolean shouldBeFalse = toTest.createUser(user, new ModelMap());
        //then
        Assertions.assertFalse(shouldBeFalse);
    }

    @Test
    @DisplayName("result should be false when we receive user without user id")
    void testingCreateAndReturnNewUser() {
        //given
        User user = new User();
        user.setPassword("password");
        user.setConfirmPassword("password");
        //and
        var mockMapper = mock(UserMapper.class);
        var mockPasswordEncoder = mock(PasswordEncoder.class);
        var mockFeignServiceUserManager = mock(FeignServiceUserManager.class);
        //system under test
        var toTest = new MainService(mockFeignServiceUserManager, null, null, mockMapper, null, mockPasswordEncoder);
        when(toTest.createAndReturnNewUser(new User())).thenReturn(user);
        //when
        boolean shouldBeFalse = toTest.createUser(user, new ModelMap());
        //then
        Assertions.assertFalse(shouldBeFalse);
    }

    @Test
    @DisplayName("authentication should be ROLE_USER")
    void testingSetAuthorityForUser() {
        //given
        User user = new User();
        user.setId(7L);
        user.setPassword("password");
        user.setConfirmPassword("password");
        //and
        var mockAdapterRepo = mock(AdapterAuthorityRepository.class);
        var toTest = new MainService(null, null, mockAdapterRepo, null, null, null);
        //when
        User userWithAuthentication = toTest.setAuthorityForUser(user);
        //then
        long shouldBe1 = userWithAuthentication.getAuthorities().stream().filter(role -> role.getAuthority().equals("ROLE_USER")).count();
        String shouldBeRoleUser = userWithAuthentication.getAuthorities().stream()
                .map(AuthorityEntity::getAuthority)
                .filter(authority -> authority.equals("ROLE_USER"))
                .findAny().get();
        //
        Assertions.assertEquals(1, shouldBe1);
        Assertions.assertEquals("ROLE_USER", shouldBeRoleUser);
    }
}
