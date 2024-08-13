package com.Abdo_Fahmi.Recipe_Bank.user;

import lombok.Builder;

// Standard DTO used to respond to the client without exposing sensitive info
// Also used for updating user information (excluding password change which will have its own DTO
@Builder
public record UserDTO(
    String id,
    String name,
    String email
) { }
