package com.Abdo_Fahmi.Recipe_Bank.service.impl;

import com.Abdo_Fahmi.Recipe_Bank.model.recipe.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Abdo_Fahmi.Recipe_Bank.repository.RecipeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService implements com.Abdo_Fahmi.Recipe_Bank.service.RecipeService {

    private final RecipeRepository recipeRepo;

    @Autowired
    public RecipeService(final RecipeRepository recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    @Override
    public Optional<Recipe> saveRecipe(Recipe recipe) {
        if(recipeRepo.existsById(recipe.getId())) return Optional.empty();
        Recipe savedRecipe = recipeRepo.save(recipe);
        return Optional.of(savedRecipe);
    }

    @Override
    public boolean deleteRecipeById(String id) {
        return recipeRepo.findById(id).map(recipe -> {
            recipeRepo.delete(recipe);
            return true;
        }).orElse(false);
    }

    @Override
    public Optional<Recipe> updateRecipe(String id, Recipe recipe) {
        return recipeRepo.findById(id).map(existingRecipe -> {
            recipe.setId(id);
            return recipeRepo.save(recipe);
        });
    }

    @Override
    public Optional<Recipe> findRecipeById(String id) {
        return recipeRepo.findById(id);
    }

    @Override
    public List<Recipe> findRecipesByTags(List<String> tagList) {
        return recipeRepo.findByTagsIn(tagList);
    }

    @Override
    public List<Recipe> findRecipesByIngredients(List<String> ingredientList) {
        return recipeRepo.findByIngredientsIn(ingredientList);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepo.findAll();
    }

    @Override
    public List<Recipe> findByOwnerId(String id) {
        return recipeRepo.findByOwnerId(id);
    }
}
