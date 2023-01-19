package com.bankwebapp.web;

import com.bankwebapp.domain.BankAccount;
import com.bankwebapp.domain.User;
import com.bankwebapp.domain.UserAccount;
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
        BankAccount bankAccount = new BankAccount();
        bankAccount.setName("Main bank account");
        bankAccount.setCurrency("PLN");
        modelMap.put("user", user);
        modelMap.put("bankAccount",bankAccount);
        return "login";
    }

    @GetMapping("/register")
    public String getRegister(ModelMap modelMap) {
        User user = new User();
        modelMap.put("user", user);
        return "register";
    }


    @PostMapping("/register")
    public String postRegister(@ModelAttribute BankAccount bankAccount,@ModelAttribute User user, ModelMap modelMap) {
        if (!userService.validUserToCreateUser(user, modelMap,bankAccount))
            return "register";
        return "redirect:/dashboard";
    }
}
