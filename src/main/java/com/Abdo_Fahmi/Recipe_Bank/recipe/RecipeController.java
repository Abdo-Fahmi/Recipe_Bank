package com.Abdo_Fahmi.Recipe_Bank.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        Optional<Recipe> newRecipe = recipeService.saveRecipe(recipe);
        return newRecipe.map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT)); // Not sure if BAD_REQUEST is better here
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable String id) {
        Optional<Recipe> recipe = recipeService.findRecipeById(id);
        return recipe.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                     .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable String id) {
        boolean deleted = recipeService.deleteRecipeById(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                       : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable String id, @RequestBody Recipe recipe) {
        Optional<Recipe> updatedRecipe = recipeService.updateRecipe(id, recipe);
        return updatedRecipe.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search/tags")
    public ResponseEntity<List<Recipe>> searchRecipesByTag(@RequestParam List<String> tags) {
        List<Recipe> searchResults = recipeService.findRecipesByTags(tags);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }

    @GetMapping("/search/ingredients")
    public ResponseEntity<List<Recipe>> searchRecipesByIngredient(@RequestParam List<String> ingredients) {
        List<Recipe> searchResults = recipeService.findRecipesByIngredients(ingredients);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }

    // TODO findByOwnerId - after implementing CustomUserDetails
}
