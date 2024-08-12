package com.Abdo_Fahmi.Recipe_Bank.model.user;

import lombok.Builder;

@Builder
public record UserRegistrationDTO(
    String name,
    String email,
    String password
) { }
