package com.Abdo_Fahmi.Recipe_Bank.security.jwt;

import com.Abdo_Fahmi.Recipe_Bank.exception.InvalidTokenException;
import com.Abdo_Fahmi.Recipe_Bank.exception.TokenNotFoundException;
import com.Abdo_Fahmi.Recipe_Bank.exception.UserNotFoundException;
import com.Abdo_Fahmi.Recipe_Bank.security.auth.JwtResponse;
import com.Abdo_Fahmi.Recipe_Bank.user.User;
import com.Abdo_Fahmi.Recipe_Bank.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final UserRepository userRepo;
    private final RefreshTokenRepository tokenRepo;
    private final JwtUtil jwtUtil;

    @Value("${application.security.jwt.access-expiry}")
    private long accessTokenTTL; // 15 minutes
    @Value("${application.security.jwt.refresh-expiry}")
    private long refreshTokenTTL; // 15 days

    public void saveRefreshToken(String token, String username){
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        RefreshToken newRefreshToken = RefreshToken.builder()
                .token(token)
                .expiry(new Date(System.currentTimeMillis() + refreshTokenTTL).toInstant())
                .userId(user.getId())
                .build();

        tokenRepo.save(newRefreshToken);
    }

    public JwtResponse refreshAccessToken(RefreshTokenRequest refreshRequest){
        String refreshToken = refreshRequest.refreshToken();

        RefreshToken token = tokenRepo.findRefreshTokenByToken(refreshToken)
                .orElseThrow(() -> new TokenNotFoundException("Token Not Found"));

        User user = userRepo.findById(token.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if(isRefreshTokenExpired(token.getExpiry())){
            tokenRepo.delete(token);
            throw new InvalidTokenException("Refresh Token Expired");
        }

        String accessToken = jwtUtil.generateToken(user.getUsername(), accessTokenTTL);

        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private boolean isRefreshTokenExpired(Instant expiry){
        return expiry.isBefore(new Date(System.currentTimeMillis()).toInstant());
    }

    private void extendRefreshToken(RefreshToken token){
        token.setExpiry(new Date(System.currentTimeMillis() + refreshTokenTTL).toInstant());
    }
}
