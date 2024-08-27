package com.Abdo_Fahmi.Recipe_Bank.security.auth;

import lombok.Builder;

// Making this a DTO in case more information is added to the response in the future
@Builder
public record JwtResponse(
    String token
) { }
