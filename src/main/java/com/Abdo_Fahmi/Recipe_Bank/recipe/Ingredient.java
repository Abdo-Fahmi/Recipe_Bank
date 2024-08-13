package com.Abdo_Fahmi.Recipe_Bank.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    private String name;
    private String quantity;
    private IngredientType type;
}
