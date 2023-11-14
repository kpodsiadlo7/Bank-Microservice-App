package com.mainapp.login;

import com.mainapp.MainService;
import com.mainapp.user.UserMapper;
import com.mainapp.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Slf4j
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