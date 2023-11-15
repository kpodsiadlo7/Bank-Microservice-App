package com.mainapp;

import com.mainapp.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;

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
        var toTest = new MainService( null,null, null);
        //when
        boolean shouldBeFalse = toTest.createUser(user, new ModelMap());
        //then
        Assertions.assertFalse(shouldBeFalse);
    }
}
