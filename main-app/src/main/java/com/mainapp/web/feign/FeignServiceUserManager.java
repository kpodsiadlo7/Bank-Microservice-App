package com.mainapp.web.feign;

import com.mainapp.web.dto.UserDto;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "user-manager", url = "http://user-manager:8090")
public interface FeignServiceUserManager {
    @GetMapping("/getuser/{userId}")
    String getUserById(@PathVariable Long userId);

    @PostMapping(value = "/createuser")
    String createUser(UserDto userDto);
}
