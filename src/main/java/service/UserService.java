package service;

import model.User;

import java.util.Optional;

public interface UserService {
    boolean registerUser(User user);
    boolean deleteUserById(String id);
    User updateUser(User user);
    Optional<User> findUserById(String id);

}