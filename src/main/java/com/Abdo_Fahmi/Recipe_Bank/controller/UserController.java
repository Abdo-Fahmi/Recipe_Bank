package com.Abdo_Fahmi.Recipe_Bank.controller;

import com.Abdo_Fahmi.Recipe_Bank.model.user.UserRegistrationDTO;
import com.Abdo_Fahmi.Recipe_Bank.model.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Abdo_Fahmi.Recipe_Bank.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService UserService) {
        this.userService = UserService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserRegistrationDTO user) {
        UserDTO userDTO = userService.registerUser(user);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @RequestBody UserDTO user) {
        UserDTO updatedUser = userService.updateUser(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
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
