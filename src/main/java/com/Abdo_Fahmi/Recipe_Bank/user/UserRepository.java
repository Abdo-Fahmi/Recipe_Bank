package com.Abdo_Fahmi.Recipe_Bank.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String name);
    Optional<User> findByUsername(String username);
}
