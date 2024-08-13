package com.Abdo_Fahmi.Recipe_Bank.recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "recipes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recipe {
    @Id
    private String id;
    private String name;
    private String description;
    private List<String> tags;
    private List<Ingredient> ingredients;

    private String ownerId;
}
