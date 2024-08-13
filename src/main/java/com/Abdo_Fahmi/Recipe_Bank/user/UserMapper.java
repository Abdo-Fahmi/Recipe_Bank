package com.Abdo_Fahmi.Recipe_Bank.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toResponseDTO(User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }

    public UserRegistrationDTO toRegistrationDTO(User entity) {
        return UserRegistrationDTO.builder()
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .build();
    }

    public User toEntity(UserDTO dto) {
        return User.builder()
                .id(dto.id())
                .name(dto.name())
                .email(dto.email())
                .build();
    }
}
