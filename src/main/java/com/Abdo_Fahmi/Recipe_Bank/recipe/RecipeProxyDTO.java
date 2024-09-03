package com.Abdo_Fahmi.Recipe_Bank.recipe;

import lombok.Builder;

// This DTO will be used when returning a large number of recipes for display
// ex. home page, my-recipes, my-favorites.
// As it will be more efficient and contains fewer data,
// if the user wishes to see a recipe in more detail,
// the client will hit the "getById" endpoint and receive the standard RecipeDTO for a detailed display

// More fields may be added later as the Recipe entity gets more robust
@Builder
public record RecipeProxyDTO(
        String id,
        String name,
        String description
) { }
