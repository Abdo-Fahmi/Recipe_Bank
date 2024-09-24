package com.Abdo_Fahmi.Recipe_Bank.security.auth;

public record RegisterRequest (
        String name,
        String email,
        String password
) { }
