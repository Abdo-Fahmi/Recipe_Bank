package com.Abdo_Fahmi.Recipe_Bank.recipe;

import com.Abdo_Fahmi.Recipe_Bank.recipe.Ingredient.Ingredient;
import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public record RecipeCreationRequest(
        String name,
        List<String> instructions,
        String description,
        boolean isPublic,
        Set<String> tags,
        List<Ingredient> ingredients
) { }