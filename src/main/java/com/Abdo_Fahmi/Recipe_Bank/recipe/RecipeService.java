package com.Abdo_Fahmi.Recipe_Bank.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepo;

    @Autowired
    public RecipeService(final RecipeRepository recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    public Optional<Recipe> saveRecipe(Recipe recipe) {
        if(recipeRepo.existsById(recipe.getId())) return Optional.empty();
        Recipe savedRecipe = recipeRepo.save(recipe);
        return Optional.of(savedRecipe);
    }

    public boolean deleteRecipeById(String id) {
        return recipeRepo.findById(id).map(recipe -> {
            recipeRepo.delete(recipe);
            return true;
        }).orElse(false);
    }

    public Optional<Recipe> updateRecipe(String id, Recipe recipe) {
        return recipeRepo.findById(id).map(existingRecipe -> {
            recipe.setId(id);
            return recipeRepo.save(recipe);
        });
    }

    public Optional<Recipe> findRecipeById(String id) {
        return recipeRepo.findById(id);
    }

    public List<Recipe> findRecipesByTags(List<String> tagList) {
        return recipeRepo.findByTagsIn(tagList);
    }

    public List<Recipe> findRecipesByIngredients(List<String> ingredientList) {
        return recipeRepo.findByIngredientsIn(ingredientList);
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepo.findAll();
    }

    public List<Recipe> findByOwnerId(String id) {
        return recipeRepo.findByOwnerId(id);
    }
}
