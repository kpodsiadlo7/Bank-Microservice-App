package com.mainapp.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/credit")
public class CreditController {

    @GetMapping
    public String getCredit(){
        return "credit";
    }
}
