package com.Abdo_Fahmi.Recipe_Bank.security.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

// Making this a DTO in case more information is added to the response in the future
@Builder
public record JwtResponse (
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("refresh_token")
        String refreshToken
) { }
