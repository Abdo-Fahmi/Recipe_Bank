package com.Abdo_Fahmi.Recipe_Bank.user.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepo;

    public Role createRole(ERole roleName) {
        Role role = Role.builder()
                        .name(roleName)
                        .build();

        return roleRepo.save(role);
    }

    public Optional<Role> getRoleByName(ERole roleName) {
        return roleRepo.findByName(roleName);
    }
}
