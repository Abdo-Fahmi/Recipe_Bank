package com.Abdo_Fahmi.Recipe_Bank.security.jwt;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

// The stateless nature of REST apis make log-outs difficult to implement
// Refresh token will be stored in a table, and when a log-out is provoked,
//
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tokens")
public class RefreshToken {
    @Id
    private String id;
    private String token;
    private Instant expiry;
    private String userId;
}
