package com.Abdo_Fahmi.Recipe_Bank.security.auth;

import com.Abdo_Fahmi.Recipe_Bank.security.jwt.RefreshTokenRequest;
import com.Abdo_Fahmi.Recipe_Bank.security.jwt.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;
    private final RefreshTokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        JwtResponse response = authService.validateUser(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest user) {
        authService.registerUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/refresh-access-token")
    public ResponseEntity<JwtResponse> refreshAccessToken(@RequestBody RefreshTokenRequest request) {
        JwtResponse response = tokenService.refreshAccessToken(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
