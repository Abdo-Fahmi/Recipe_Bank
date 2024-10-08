package com.Abdo_Fahmi.Recipe_Bank.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    // Will be using a symmetric key for token generation for now
    // An asymmetric approach (generating a private/public pair using openssl)
    // is naturally more secure
    // Might implement that in the future
    @Value("${application.security.jwt.secret-key}")
    private String jwtSecretKey;

    public String generateToken(String name, long expiryDate) {
        Map<String, Object> claims = new HashMap<>();
        
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(name)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiryDate))
                .and()
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        return new SecretKeySpec(jwtSecretKey.getBytes(), "HmacSHA256");
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public boolean validateToken(String token, String username) {
        String name = extractUsername(token);
        return (name.equals(username) && !isTokenExpired(token));
    }

    private Date extractExpirationDate(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    private <T> T extractClaims(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
