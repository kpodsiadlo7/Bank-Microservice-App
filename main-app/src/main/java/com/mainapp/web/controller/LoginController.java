package com.mainapp.web.controller;

import com.mainapp.service.mapper.UserMapper;
import com.mainapp.service.UserService;
import com.mainapp.web.dto.UserDto;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
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
        if (!userService.createUser(userMapper.mapToUserFromUserDto(userDto),modelMap))
            return "register";
        return "redirect:/dashboard";
    }
}
