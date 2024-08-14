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
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        List<RecipeDTO> recipes = recipeService.getAllRecipes();
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RecipeDTO> createRecipe(@RequestBody RecipeCreationDTO recipe) {
        RecipeDTO newRecipe = recipeService.saveRecipe(recipe);
        return new ResponseEntity<>(newRecipe, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable String id) {
        RecipeDTO recipe = recipeService.findRecipeById(id);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable String id) {
        recipeService.deleteRecipeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable String id, @RequestBody RecipeDTO recipe) {
        RecipeDTO updatedRecipe = recipeService.updateRecipe(id, recipe);
        return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
    }

    @GetMapping("/search/tags")
    public ResponseEntity<List<RecipeDTO>> searchRecipesByTag(@RequestParam List<String> tags) {
        List<RecipeDTO> searchResults = recipeService.findRecipesByTags(tags);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }

    @GetMapping("/search/ingredients")
    public ResponseEntity<List<RecipeDTO>> searchRecipesByIngredient(@RequestParam List<String> ingredients) {
        List<RecipeDTO> searchResults = recipeService.findRecipesByIngredients(ingredients);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }

    // TODO findByOwnerId - after implementing CustomUserDetails
}
