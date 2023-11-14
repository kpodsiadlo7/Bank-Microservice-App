package com.mainapp.login;

import com.mainapp.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    String getLogin(ModelMap modelMap) {
        UserDto userDto = new UserDto();
        modelMap.put("userDto", userDto);
        return "login";
    }

    @GetMapping("/register")
    String getRegister(ModelMap modelMap) {
        UserDto userDto = new UserDto();
        modelMap.put("userDto", userDto);
        return "register";
    }

    @PostMapping("/register")
    String postRegister(@ModelAttribute UserDto userDto, ModelMap modelMap) {
        log.info(userDto.toString());
        return loginService.registerUser(userDto,modelMap);
    }
}
