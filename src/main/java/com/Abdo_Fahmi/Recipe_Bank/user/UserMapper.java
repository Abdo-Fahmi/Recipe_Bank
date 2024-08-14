package com.Abdo_Fahmi.Recipe_Bank.user;

import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;
@Component
@UtilityClass
public class UserMapper {
    public UserDTO toResponseDTO(User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }
}