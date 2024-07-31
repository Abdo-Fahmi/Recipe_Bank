package service.impl;

import model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.Optional;

@Service
public class UserService implements service.UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public void deleteById(String id) {
        userRepo.deleteById(id);
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public User updateUser(User user) {
        if(!userRepo.existsById(user.getId())) throw new RuntimeException("User not found");
        return userRepo.save(user);
    }
}
