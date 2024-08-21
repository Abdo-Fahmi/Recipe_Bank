package com.Abdo_Fahmi.Recipe_Bank.security.auth;

public record LogInRequest (
        String name,
        String password
) { }
