package com.mainapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MainAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainAppApplication.class, args);
    }

}
