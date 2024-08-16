package com.Abdo_Fahmi.Recipe_Bank.user;

import com.Abdo_Fahmi.Recipe_Bank.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public void deleteUserById(String id) {
        if(!userRepo.existsById(id)) throw new UserNotFoundException("User doesn't exist");
        userRepo.deleteById(id);
    }

    public UserDTO updateUser(String id, UserDTO user) {
        User updatedUser = userRepo.findById(id)
                                   .orElseThrow(() -> new UserNotFoundException("User doesn't exists"));

        // Check if the name is already taken by another user
        // The first check is to make sure the name/email are different (being changed) before we check for duplicates
        if (!updatedUser.getName().equals(user.name()) && userRepo.existsByName(user.name()))
            throw new NameAlreadyInUseException("Name is already taken by another user");

        // Check if the email is already taken by another user
        if (!updatedUser.getEmail().equals(user.email()) && userRepo.existsByEmail(user.email()))
            throw new EmailAlreadyInUseException("Email is already in use");

        // Applying changes
        updatedUser.setName(user.name());
        updatedUser.setEmail(user.email());

        updatedUser = userRepo.save(updatedUser);

        return UserMapper.toResponseDTO(updatedUser);
    }

    public void changePassword(String id, PasswordChangeRequest request) {
        User user = userRepo.findById(id)
                            .orElseThrow(() -> new UserNotFoundException("User doesn't exists"));

        if(!passwordEncoder.matches(request.oldPassword(),(user.getPassword())))
            throw new IncorrectCredentialsException("Incorrect password");
        if (!passwordEncoder.matches(request.newPassword(),(request.repeatPassword())))
            throw new PasswordsDoNotMatchException("Password are not the same");

        user.setPassword(passwordEncoder.encode(request.newPassword()));

        userRepo.save(user);
    }

    public UserDTO findUserById(String userId) {
        User user = userRepo.findById(userId)
                            .orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserMapper.toResponseDTO(user);
    }

    public UserDTO findUserByName(String name) {
        User user = userRepo.findByName(name)
                            .orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserMapper.toResponseDTO(user);
    }
}
