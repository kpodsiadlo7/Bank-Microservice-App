package com.mainapp.service;

import com.mainapp.service.mapper.UserMapper;
import com.mainapp.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MainService mainService;
    private final UserMapper userMapper;

    public String registerUser(final UserDto userDto, final ModelMap modelMap) {
        try {
            if (!mainService.createUser(userMapper.mapToUserFromUserDto(userDto), modelMap))
                return "register";
        } catch (Exception e) {
            modelMap.put("error", "Error with register, try again");
            return "register";
        }
        return "redirect:/dashboard";
    }
}
