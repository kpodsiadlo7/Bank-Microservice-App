package com.mainapp.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
class HomeController {

    @GetMapping
    String getHome() {
        return "redirect:/login";
    }
}
