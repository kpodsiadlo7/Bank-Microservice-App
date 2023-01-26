package com.mainapp.web.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "user-manager", url = "")
public interface FeignServiceUtil {
}
