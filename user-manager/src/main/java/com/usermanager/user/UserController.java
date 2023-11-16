package com.usermanager.user;

import com.usermanager.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
class UserController {
    private final UserFacade userFacade;

    @GetMapping("/login")
    ResponseEntity<UserDto> loginUser(@RequestParam String username) {
        return ResponseEntity.ok(userFacade.loginUser(username));
    }
    @GetMapping("/check-user")
    ResponseEntity<Boolean> checkUserInDatabaseByUsername(@RequestParam String username){
        return ResponseEntity.ok(userFacade.checkIfUserExistInDbWithThisUsername(username));
    }

    @GetMapping("/get-user")
    ResponseEntity<UserDto> getUserById(@RequestParam Long userId){
        return ResponseEntity.ok(userFacade.getUserById(userId));
    }
    @PostMapping("/create-user")
    ResponseEntity<UserDto> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userFacade.validateData(user));
    }

}
