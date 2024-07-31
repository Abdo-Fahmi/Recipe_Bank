package service;

import model.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    Recipe saveRecipe(Recipe recipe);
    boolean deleteRecipeById(String id);
    Optional<Recipe> findRecipeById(String id);
    List<Recipe> findRecipesByTags(List<String> tagsList);
    List<Recipe> getAllRecipes();
    List<Recipe> findRecipesByIngredients(List<String> ingredientList);
    List<Recipe> findByOwnerId(String id);
    Optional<Recipe> updateRecipe(String id, Recipe recipe);
}
