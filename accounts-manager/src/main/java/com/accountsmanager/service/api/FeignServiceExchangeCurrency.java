package com.accountsmanager.service.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "currency-api", url = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/")
interface FeignServiceExchangeCurrency {

    @GetMapping("{from}/{to}.json")
    CurrencyApi getRate(@PathVariable String from, @PathVariable String to);
}
