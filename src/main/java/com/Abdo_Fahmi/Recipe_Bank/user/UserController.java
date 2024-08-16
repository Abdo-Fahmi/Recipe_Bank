package com.Abdo_Fahmi.Recipe_Bank.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @RequestBody UserDTO user) {
        UserDTO updatedUser = userService.updateUser(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PatchMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable String id, @RequestBody PasswordChangeRequest passwordChangeRequest) {
        userService.changePassword(id, passwordChangeRequest);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable String id) {
        UserDTO foundUserDTO = userService.findUserById(id);
        return new ResponseEntity<>(foundUserDTO, HttpStatus.FOUND);
    }

    @GetMapping("/search")
    public ResponseEntity<UserDTO> findUserByName(@RequestParam String name) {
        UserDTO foundUserDTO = userService.findUserByName(name);
        return new ResponseEntity<>(foundUserDTO, HttpStatus.FOUND);
    }
}
