package com.mainapp.web.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "user-manager", url = "http://user-manager:8090")
public interface FeignServiceUserManager {
    @GetMapping("/test")
    String getTest();
}
