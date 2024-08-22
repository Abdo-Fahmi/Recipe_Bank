package com.Abdo_Fahmi.Recipe_Bank.user;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public UserDTO toResponseDTO(User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .role(entity.getRoles())
                .build();
    }
}