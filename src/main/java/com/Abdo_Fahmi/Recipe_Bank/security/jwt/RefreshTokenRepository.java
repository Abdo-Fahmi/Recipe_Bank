package com.Abdo_Fahmi.Recipe_Bank.security.jwt;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
    Optional<RefreshToken> findRefreshTokenByToken(String token);
}
