package com.Abdo_Fahmi.Recipe_Bank.recipe;


import lombok.Getter;

@Getter
public enum IngredientType {
    GRAM("g"),
    KILOGRAM("kg"),
    LITER("L"),
    MILLILITER("ml"),
    PIECE("pcs"),
    TEASPOON("tsp"),
    TABLESPOON("tbs"),
    CUP("c");

    private final String unit;

    IngredientType(String unit) {
        this.unit = unit;
    }
}
