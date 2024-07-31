package service;

import model.User;

import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    void deleteById(String id);
    User updateUser(User user);
    Optional<User> findById(String id);
    Optional<User> findByUsername(String username);
}