package com.Abdo_Fahmi.Recipe_Bank.recipe;

import com.Abdo_Fahmi.Recipe_Bank.exception.RecipeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepo;

    public RecipeDTO saveRecipe(String id, RecipeCreationRequest recipe) {
        Recipe newRecipe = RecipeMapper.toEntity(recipe);
        newRecipe.setOwnerId(id);

        recipeRepo.save(newRecipe);
        return RecipeMapper.toDTO(newRecipe);
    }

    public void deleteRecipeById(String id, String currentUserId) {
        Recipe recipe = recipeRepo.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));

        // Check is the logged-in user has authorization to delete this recipe.
        if (!recipe.getOwnerId().equals(currentUserId)) throw new AccessDeniedException("User is not authorized for this action");
        recipeRepo.deleteById(id);
    }

    public RecipeDTO updateRecipe(String id, RecipeDTO recipeDTO, String currentUserId) {
        Recipe recipe = recipeRepo.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));

        // Check is the logged-in user has authorization to delete this recipe.
        if (!recipe.getOwnerId().equals(currentUserId)) throw new AccessDeniedException("User is not authorized for this action");
        Recipe updatedRecipe = RecipeMapper.toEntity(recipeDTO);

        recipeRepo.save(updatedRecipe);
        return RecipeMapper.toDTO(updatedRecipe);
    }

    public RecipeDTO findRecipeById(String id) {
        Recipe foundRecipe = recipeRepo.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));

        return RecipeMapper.toDTO(foundRecipe);
    }

    // TODO adjust the search methods to take visibility into account
    public List<RecipeProxyDTO> findRecipesByTags(List<String> tagList) {
        return recipeRepo.findByTagsIn(tagList)
                .stream()
                .map(RecipeMapper::toProxyDTO)
                .collect(Collectors.toList());
    }

    public List<RecipeProxyDTO> findRecipesByIngredients(List<String> ingredientList) {
        return recipeRepo.findByIngredientsIn(ingredientList)
                .stream()
                .map(RecipeMapper::toProxyDTO)
                .collect(Collectors.toList());
    }

    public List<RecipeProxyDTO> getAllRecipes() {
        return recipeRepo.findAll()
                .stream()
                .map(RecipeMapper::toProxyDTO)
                .collect(Collectors.toList());
    }

    public List<RecipeProxyDTO> findByOwnerId(String id) {
        return recipeRepo.findByOwnerId(id)
                .stream()
                .map(RecipeMapper::toProxyDTO)
                .collect(Collectors.toList());
    }

    public List<RecipeProxyDTO> getRecipesById(Set<String> favoritesIDs) {
        return recipeRepo.findAllById(favoritesIDs)
                .stream()
                .map(RecipeMapper::toProxyDTO)
                .collect(Collectors.toList());
    }
}