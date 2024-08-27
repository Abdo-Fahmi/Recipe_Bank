package com.Abdo_Fahmi.Recipe_Bank.security.auth;

public record LoginRequest(
        String name,
        String password
) { }
