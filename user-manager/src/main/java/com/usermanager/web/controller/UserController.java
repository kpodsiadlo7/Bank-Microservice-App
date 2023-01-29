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

    @GetMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestParam String username) {
        return ResponseEntity.ok(userMapper.mapToUserDtoFromUser(userService.loginUser(username)));
    }

    @PostMapping("/create-user")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userMapper.mapToUserDtoFromUser(userService.validateData(userMapper.mapToUserFromUserDto(userDto))));
    }

    @GetMapping("/check-user")
    public ResponseEntity<Boolean> checkUserInDatabaseByUsername(@RequestParam String username){
        return ResponseEntity.ok(userService.checkIfUserExistInDbWithThisUsername(username));
    }
}
