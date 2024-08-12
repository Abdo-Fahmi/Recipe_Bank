package com.Abdo_Fahmi.Recipe_Bank.service.impl;

import com.Abdo_Fahmi.Recipe_Bank.exception.EmailAlreadyInUseException;
import com.Abdo_Fahmi.Recipe_Bank.exception.NameAlreadyInUseException;
import com.Abdo_Fahmi.Recipe_Bank.exception.UserNotFoundException;
import com.Abdo_Fahmi.Recipe_Bank.model.user.User;
import com.Abdo_Fahmi.Recipe_Bank.service.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.Abdo_Fahmi.Recipe_Bank.model.user.UserRegistrationDTO;
import com.Abdo_Fahmi.Recipe_Bank.model.user.UserDTO;
import com.Abdo_Fahmi.Recipe_Bank.model.user.UserMapper;
import com.Abdo_Fahmi.Recipe_Bank.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO registerUser(UserRegistrationDTO user) {
        if(userRepo.existsByName(user.name())) throw new NameAlreadyInUseException("Name is already taken by another user");
        if(userRepo.existsByEmail(user.email())) throw new EmailAlreadyInUseException("Email is already in use");

        User newUser = User.builder()
                .email(user.email())
                .name(user.name())
                .password(passwordEncoder.encode(user.password()))
                .build();

        newUser = userRepo.save(newUser);

        return userMapper.toResponseDTO(newUser);
    }

    @Override
    public void deleteUserById(String id) {
        if(!userRepo.existsById(id)) throw new UserNotFoundException("User doesn't exist");
        userRepo.deleteById(id);
    }

    @Override
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

        return userMapper.toResponseDTO(updatedUser);
    }

    @Override
    public UserDTO findUserById(String userId) {
        User user = userRepo.findById(userId)
                            .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserDTO findUserByName(String name) {
        User user = userRepo.findById(name)
                            .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.toResponseDTO(user);
    }
}
