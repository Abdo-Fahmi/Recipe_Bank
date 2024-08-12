package com.Abdo_Fahmi.Recipe_Bank.repository;

import com.Abdo_Fahmi.Recipe_Bank.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail(String email);
    boolean existsByName(String name);
    Optional<User> findByName(String name);
}
