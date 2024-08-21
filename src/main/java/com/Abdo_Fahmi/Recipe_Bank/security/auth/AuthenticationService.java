package com.Abdo_Fahmi.Recipe_Bank.security.auth;

import com.Abdo_Fahmi.Recipe_Bank.exception.EmailAlreadyInUseException;
import com.Abdo_Fahmi.Recipe_Bank.exception.NameAlreadyInUseException;
import com.Abdo_Fahmi.Recipe_Bank.user.User;
import com.Abdo_Fahmi.Recipe_Bank.user.UserDTO;
import com.Abdo_Fahmi.Recipe_Bank.user.UserMapper;
import com.Abdo_Fahmi.Recipe_Bank.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    // TODO Change response from user DTO to authentication response
    public UserDTO registerUser(RegisterRequest user) {
        if(userRepo.existsByName(user.name())) throw new NameAlreadyInUseException("Name is already taken by another user");
        if(userRepo.existsByEmail(user.email())) throw new EmailAlreadyInUseException("Email is already in use");

        User newUser = User.builder()
                .email(user.email())
                .name(user.name())
                .password(user.password())
                .build();

        newUser = userRepo.save(newUser);

        return UserMapper.toResponseDTO(newUser);
    }
}
