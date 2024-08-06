package service.impl;

import model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.UserRepository;
import service.CounterService;

import java.util.Optional;

@Service
public class UserService implements service.UserService {

    private final UserRepository userRepo;
    private final CounterService counterService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, CounterService counterService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.counterService = counterService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> registerUser(User user) {
        if(userRepo.existsByEmail(user.getEmail())) return Optional.empty(); // Email already exists

        user.setId(counterService.getNextSequence("user"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userRepo.save(user);
        return Optional.of(newUser);
    }

    @Override
    public boolean deleteUserById(String id) {
        return userRepo.findById(id).map(user -> {
            userRepo.delete(user);
            return true;
        }).orElse(false);
    }

    @Override
    public Optional<User> updateUser(String id, User user) {
        return userRepo.findById(user.getId()).map(existingUser ->{
            user.setId(id); // Making sure the id stays the same after updating
            user.setPassword(passwordEncoder.encode(user.getPassword())); // re-encoding the password before saving the user again
            return Optional.of(userRepo.save(user));
        }).orElse(Optional.empty());
    }

    @Override
    public Optional<User> findUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> findUserByName(String name) {
        return userRepo.findByName(name);
    }
}
