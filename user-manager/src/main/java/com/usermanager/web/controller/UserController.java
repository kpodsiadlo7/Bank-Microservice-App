package com.usermanager.web.controller;

import com.usermanager.service.UserService;
import com.usermanager.service.mapper.UserMapper;
import com.usermanager.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/getuser/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable final Long userId) {
        return ResponseEntity.ok(userMapper.mapToUserDtoFromUser(userService.getUserById(userId)));
    }

    @PostMapping("/createuser")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.createUser(userMapper.mapToUserFromUserDto(userDto)));
    }
}
