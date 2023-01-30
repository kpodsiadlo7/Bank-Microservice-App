package com.mainapp.web.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "transactions-manager", url = "http://transactions-manager:8000")
public interface FeignServiceTransactionsManager {

    @GetMapping
    String test();

}
