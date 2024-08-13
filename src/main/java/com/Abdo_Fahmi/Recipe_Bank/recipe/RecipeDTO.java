package com.Abdo_Fahmi.Recipe_Bank.recipe;

import lombok.Builder;

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
