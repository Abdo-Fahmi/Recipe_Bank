package com.Abdo_Fahmi.Recipe_Bank.security.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    // Will be using a symmetric key for token generation for now
    // An asymmetric approach (generating a private/public pair using openssl) is naturally more secure
    // Might implement that in the future
    @Value("${JWT_KEY}")
    private String jwtSecretKey;

    public String generateToken(String name) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(name)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 60))
                .and()
                .signWith(new SecretKeySpec(jwtSecretKey.getBytes(), "HmacSHA256")) // unsure about this way of signing the key, best one I found
                .compact();
    }
}
