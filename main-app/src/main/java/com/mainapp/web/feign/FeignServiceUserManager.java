package com.mainapp.web.feign;

import com.mainapp.web.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-manager", url = "http://user-manager:8090")
public interface FeignServiceUserManager {

    @PostMapping(value = "/create-user")
    UserDto createUser(@RequestBody UserDto userDto);

    @GetMapping("/login")
    UserDto loginUser(@RequestParam String username);

    @GetMapping("/check-user")
    boolean checkIfExistInDb(@RequestParam String username);
}
