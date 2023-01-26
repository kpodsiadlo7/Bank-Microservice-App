package com.mainapp.web.controller;

import com.mainapp.service.mapper.UserMapper;
import com.mainapp.service.UserService;
import com.mainapp.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String postRegister(@ModelAttribute UserDto userDto, ModelMap modelMap){
        if (!userService.validUserData(userMapper.mapToUserFromUserDto(userDto),modelMap))
            return "register";
        return "redirect:/dashboard";
    }
}
