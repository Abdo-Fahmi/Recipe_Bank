package com.Abdo_Fahmi.Recipe_Bank.recipe;

import com.Abdo_Fahmi.Recipe_Bank.recipe.Ingredient.Ingredient;
import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public record RecipeDTO (
        String id,
        String name,
        String description,
        List<String> instructions,
        boolean isPublic,
        Set<String> tags,
        List<Ingredient> ingredients,
        String ownerId
) { }
