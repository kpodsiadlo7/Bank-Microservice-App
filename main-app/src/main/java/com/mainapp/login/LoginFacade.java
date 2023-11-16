package com.mainapp.login;

import com.mainapp.MainService;
import com.mainapp.user.dto.UserDto;
import com.mainapp.user.UserFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginFacade {
    private final MainService mainService;
    private final UserFacade userFacade;

    String registerUser(final UserDto userDto, final ModelMap modelMap) {
        try {
            if (!mainService.createUser(userFacade.mapToUserFromUserDto(userDto), modelMap))
                return "register";
        } catch (Exception e) {
            modelMap.put("error", "Error with register, try again");
            return "register";
        }
        return "redirect:/dashboard";
    }
}
