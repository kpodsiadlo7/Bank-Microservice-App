package com.accountsmanager.web.feign;

import com.accountsmanager.service.data.Currency;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "currency-api", url = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/")
public interface FeignServiceExchangeCurrency {

    @GetMapping("{from}/{to}.json")
    Currency getRate(@PathVariable String from, @PathVariable String to);
}
