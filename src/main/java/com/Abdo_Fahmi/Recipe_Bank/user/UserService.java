package com.Abdo_Fahmi.Recipe_Bank.user;

import com.Abdo_Fahmi.Recipe_Bank.exception.EmailAlreadyInUseException;
import com.Abdo_Fahmi.Recipe_Bank.exception.NameAlreadyInUseException;
import com.Abdo_Fahmi.Recipe_Bank.exception.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO registerUser(UserRegistrationDTO user) {
        if(userRepo.existsByName(user.name())) throw new NameAlreadyInUseException("Name is already taken by another user");
        if(userRepo.existsByEmail(user.email())) throw new EmailAlreadyInUseException("Email is already in use");

        User newUser = User.builder()
                .email(user.email())
                .name(user.name())
                .password(passwordEncoder.encode(user.password()))
                .build();

        newUser = userRepo.save(newUser);

        return UserMapper.toResponseDTO(newUser);
    }

    public void deleteUserById(String id) {
        if(!userRepo.existsById(id)) throw new UserNotFoundException("User doesn't exist");
        userRepo.deleteById(id);
    }

    public UserDTO updateUser(String id, UserDTO user) {
        User updatedUser = userRepo.findById(id)
                                   .orElseThrow(() -> new UserNotFoundException("User doesn't exists"));

        // Checking if the provided info to update is already in use or not
        if(userRepo.existsByName(user.name())) throw new NameAlreadyInUseException("Name is already taken by another user");
        if(userRepo.existsByEmail(user.email())) throw new EmailAlreadyInUseException("Email is already in use");

        // Applying changes
        updatedUser.setName(user.name());
        updatedUser.setEmail(user.email());

        updatedUser = userRepo.save(updatedUser);

        return UserMapper.toResponseDTO(updatedUser);
    }

    public UserDTO findUserById(String userId) {
        User user = userRepo.findById(userId)
                            .orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserMapper.toResponseDTO(user);
    }

    public UserDTO findUserByName(String name) {
        User user = userRepo.findById(name)
                            .orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserMapper.toResponseDTO(user);
    }
}
