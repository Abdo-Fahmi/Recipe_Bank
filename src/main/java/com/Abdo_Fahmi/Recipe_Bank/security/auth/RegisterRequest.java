package com.Abdo_Fahmi.Recipe_Bank.security.auth;

import lombok.Builder;

@Builder
public record RegisterRequest (
    String name,
    String email,
    String password
) { }
