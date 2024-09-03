package com.Abdo_Fahmi.Recipe_Bank.user;

import com.Abdo_Fahmi.Recipe_Bank.user.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    private String password;
    private String email;
    private Set<Role> roles;
    private Set<String> favorites; // Favorites will be a set of the recipe IDs

    @CreatedDate
    private LocalDateTime creationDate;
    @LastModifiedDate
    private LocalDateTime lastModified;
}
