package com.Abdo_Fahmi.Recipe_Bank.user;

import lombok.Builder;

@Builder
public record PasswordChangeRequest (
        String oldPassword,
        String newPassword,
        String repeatPassword
) { }
