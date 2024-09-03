package com.Abdo_Fahmi.Recipe_Bank.recipe;

import com.Abdo_Fahmi.Recipe_Bank.recipe.Ingredient.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "recipes")
public class Recipe {
    @Id
    private String id;
    private String name;
    private String description;
    private List<String> instructions; // Could be a list class `Step` to allow more functionality but this is enough for now
    private Set<String> tags;
    private boolean isPublic;
    private List<Ingredient> ingredients;
    private String ownerId;

    @CreatedDate
    private LocalDateTime creationDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
