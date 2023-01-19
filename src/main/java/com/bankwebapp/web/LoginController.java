package com.bankwebapp.controller;

import com.bankwebapp.domain.User;
import com.bankwebapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLogin(ModelMap modelMap) {
        User user = new User();
        modelMap.put("user", user);
        return "login";
    }

    @GetMapping("/register")
    public String getRegister(ModelMap modelMap) {
        User user = new User();
        modelMap.put("user", user);
        return "register";
    }


    @PostMapping("/register")
    public String postRegister(@ModelAttribute User user, ModelMap modelMap) {
        if (!userService.validUser(user, modelMap))
            return "register";
        return "redirect:/dashboard";
    }
}
