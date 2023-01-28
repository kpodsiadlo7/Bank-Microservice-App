package com.mainapp.web.controller;

import com.mainapp.web.feign.FeignServiceUserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping
    public String getHome(){
        return "redirect:/login";
    }
}
