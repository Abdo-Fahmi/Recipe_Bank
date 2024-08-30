package com.Abdo_Fahmi.Recipe_Bank.user;

import com.Abdo_Fahmi.Recipe_Bank.security.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PatchMapping("/update-account")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user) {
        // Retrieving authentication from the context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Fetching data of the currently authenticated user
        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();

        UserDTO updatedUser = userService.updateUser(currentUser.getId(), user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PatchMapping("/update-account/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) {
        // Redundant, but making the currentUser a class variable but that may be a security vulnerability
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal currentUser = (UserPrincipal) authentication.getPrincipal();

        userService.changePassword(currentUser.getId(), passwordChangeRequest);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable String id) {
        UserDTO foundUserDTO = userService.findUserById(id);
        return new ResponseEntity<>(foundUserDTO, HttpStatus.FOUND);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<UserDTO> findUserByName(@PathVariable String name) {
        UserDTO foundUserDTO = userService.findUserByName(name);
        return new ResponseEntity<>(foundUserDTO, HttpStatus.FOUND);
    }
}
