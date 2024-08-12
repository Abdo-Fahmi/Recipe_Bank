package com.Abdo_Fahmi.Recipe_Bank.model.recipe;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
public record RecipeDTO (
    String id,
    String name,
    String description,
    List<String> tags,
    List<Ingredient> ingredients,
    String ownerId
) { }
