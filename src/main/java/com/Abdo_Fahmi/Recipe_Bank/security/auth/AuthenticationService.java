package com.Abdo_Fahmi.Recipe_Bank.security.auth;

import com.Abdo_Fahmi.Recipe_Bank.exception.EmailAlreadyInUseException;
import com.Abdo_Fahmi.Recipe_Bank.exception.NameAlreadyInUseException;
import com.Abdo_Fahmi.Recipe_Bank.security.jwt.JwtUtil;
import com.Abdo_Fahmi.Recipe_Bank.security.jwt.RefreshTokenRepository;
import com.Abdo_Fahmi.Recipe_Bank.security.jwt.RefreshTokenService;
import com.Abdo_Fahmi.Recipe_Bank.user.User;
import com.Abdo_Fahmi.Recipe_Bank.user.UserRepository;
import com.Abdo_Fahmi.Recipe_Bank.user.role.ERole;
import com.Abdo_Fahmi.Recipe_Bank.user.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService tokenService;

    @Value("${application.security.jwt.access-expiry}")
    private long accessTokenTTL; // 15 minutes
    @Value("${application.security.jwt.refresh-expiry}")
    private long refreshTokenTTL; // 15 days

    private final RefreshTokenRepository tokenRepo;

    public void registerUser(RegisterRequest user) {
        if (userRepo.existsByUsername(user.name()))
            throw new NameAlreadyInUseException("Name is already taken by another user");
        if (userRepo.existsByEmail(user.email()))
            throw new EmailAlreadyInUseException("Email is already in use");

        // Filling provided info for the new user
        // A new user only has the default role of ROLE_USER
        User newUser = User.builder()
                .email(user.email())
                .username(user.name())
                .password(passwordEncoder.encode(user.password()))
                .roles(Collections.singleton(roleService.createRole(ERole.ROLE_USER)))
                .build();

        userRepo.save(newUser);
    }

    public JwtResponse validateUser(LoginRequest loginRequest) {
        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.name(),
                                loginRequest.password())
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generating a new pair of access/refresh token on new login
        String accessToken = jwtUtil.generateToken(loginRequest.name(), accessTokenTTL);
        String refreshToken = jwtUtil.generateToken(loginRequest.name(), refreshTokenTTL);

        // Saving the new refresh token in the DB
        tokenService.saveRefreshToken(refreshToken, loginRequest.name());

        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
