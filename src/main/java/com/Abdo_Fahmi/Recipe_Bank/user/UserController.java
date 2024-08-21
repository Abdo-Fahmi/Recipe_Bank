package com.Abdo_Fahmi.Recipe_Bank.user;

import com.Abdo_Fahmi.Recipe_Bank.security.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication();
        UserDTO updatedUser = userService.updateUser(currentUser.getId(), user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PatchMapping("/update-account/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) {
        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication();
        userService.changePassword(currentUser.getId(), passwordChangeRequest);
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
