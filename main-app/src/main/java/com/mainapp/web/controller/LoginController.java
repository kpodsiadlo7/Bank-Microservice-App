package com.mainapp.web.controller;

import com.mainapp.service.mapper.UserMapper;
import com.mainapp.service.MainService;
import com.mainapp.web.dto.UserDto;
import com.mainapp.web.feign.FeignServiceAccountsManager;
import com.mainapp.web.feign.FeignServiceUserManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MainService mainService;
    private final UserMapper userMapper;

    @GetMapping("/login")
    public String getLogin(ModelMap modelMap) {
        UserDto userDto = new UserDto();
        modelMap.put("userDto", userDto);
        return "login";
    }

    @GetMapping("/register")
    public String getRegister(ModelMap modelMap){
        UserDto userDto = new UserDto();
        modelMap.put("userDto",userDto);
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute UserDto userDto,ModelMap modelMap){
        log.info(userDto.toString());
        if (!mainService.createUser(userMapper.mapToUserFromUserDto(userDto),modelMap))
            return "register";
        return "redirect:/dashboard";
    }
}
