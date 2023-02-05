package com.accountsmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AccountsManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsManagerApplication.class, args);
    }

}
