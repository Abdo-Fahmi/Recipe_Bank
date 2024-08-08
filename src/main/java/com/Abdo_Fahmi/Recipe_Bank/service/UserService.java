package com.Abdo_Fahmi.Recipe_Bank.service;

import com.Abdo_Fahmi.Recipe_Bank.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> registerUser(User user);
    boolean deleteUserById(String id);
    Optional<User> updateUser(String id, User user);
    Optional<User> findUserById(String id);
    Optional<User> findUserByName(String name);
}