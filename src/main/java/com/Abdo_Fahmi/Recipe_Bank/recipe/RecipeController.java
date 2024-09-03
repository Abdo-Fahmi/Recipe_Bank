package com.Abdo_Fahmi.Recipe_Bank.recipe;

import com.Abdo_Fahmi.Recipe_Bank.security.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<RecipeProxyDTO>> getAllRecipes() {
        List<RecipeProxyDTO> recipes = recipeService.getAllRecipes();
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RecipeDTO> createRecipe(@RequestBody RecipeCreationRequest recipe, @AuthenticationPrincipal UserPrincipal currentUser) {
        RecipeDTO newRecipe = recipeService.saveRecipe(currentUser.getId(), recipe);
        return new ResponseEntity<>(newRecipe, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable String id) {
        RecipeDTO recipe = recipeService.findRecipeById(id);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable String id, @AuthenticationPrincipal UserPrincipal currentUser) {
        recipeService.deleteRecipeById(id, currentUser.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable String recipeId, @RequestBody RecipeDTO recipeDTO, @AuthenticationPrincipal UserPrincipal currentUser) {
        RecipeDTO updatedRecipe = recipeService.updateRecipe(recipeId, recipeDTO, currentUser.getId());
        return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
    }

    @GetMapping("/search/tags")
    public ResponseEntity<List<RecipeProxyDTO>> searchRecipesByTag(@RequestParam List<String> tags) {
        List<RecipeProxyDTO> searchResults = recipeService.findRecipesByTags(tags);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }

    @GetMapping("/search/ingredients")
    public ResponseEntity<List<RecipeProxyDTO>> searchRecipesByIngredient(@RequestParam List<String> ingredients) {
        List<RecipeProxyDTO> searchResults = recipeService.findRecipesByIngredients(ingredients);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }
}
