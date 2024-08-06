package controller;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // return CREATED on successful register
    //        CONFLICT if requested email is already in use
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return userService.registerUser(user)
                .map(newUser -> new ResponseEntity<>(newUser, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    // return NOCONTENT on successful delete
    //        NOTFOUND if user doesn't exist
    @DeleteMapping("/{id")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        return userService.deleteUserById(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // return OK on successful update
    //        NOTFOUND if user doesn't exist
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        return userService.updateUser(id, user)
                .map(updatedUser -> new ResponseEntity<>(updatedUser, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // return OK on successful update
    //        NOTFOUND if user doesn't exist
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable String id) {
        return userService.findUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // return OK on successful update
    //        NOTFOUND if user doesn't exist
    @GetMapping("/search")
    public ResponseEntity<User> findUserByName(@RequestParam String name) {
        return userService.findUserByName(name)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
