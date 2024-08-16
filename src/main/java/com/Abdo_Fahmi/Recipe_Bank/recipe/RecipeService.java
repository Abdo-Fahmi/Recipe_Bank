package com.Abdo_Fahmi.Recipe_Bank.recipe;

import com.Abdo_Fahmi.Recipe_Bank.exception.RecipeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepo;

    public RecipeDTO saveRecipe(RecipeCreationRequest recipe) {
        Recipe newRecipe = RecipeMapper.toEntity(recipe);
        recipeRepo.save(newRecipe);

        return RecipeMapper.toDTO(newRecipe);
    }

    public void deleteRecipeById(String id) {
        if(!recipeRepo.existsById(id)) throw new RecipeNotFoundException("Recipe not found");

        recipeRepo.deleteById(id);
    }

    public RecipeDTO updateRecipe(String id, RecipeDTO recipe) {
        if(!recipeRepo.existsById(id)) throw new RecipeNotFoundException("Recipe not found");
        Recipe updatedRecipe = RecipeMapper.toEntity(recipe);

        return RecipeMapper.toDTO(updatedRecipe);
    }

    public RecipeDTO findRecipeById(String id) {
        Recipe foundRecipe = recipeRepo.findById(id)
                                       .orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));

        return RecipeMapper.toDTO(foundRecipe);
    }

    public List<RecipeDTO> findRecipesByTags(List<String> tagList) {
        return recipeRepo.findByTagsIn(tagList)
                         .stream()
                         .map(RecipeMapper::toDTO)
                         .collect(Collectors.toList());
    }

    public List<RecipeDTO> findRecipesByIngredients(List<String> ingredientList) {
        return recipeRepo.findByIngredientsIn(ingredientList)
                .stream()
                .map(RecipeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<RecipeDTO> getAllRecipes() {
        return recipeRepo.findAll()
                         .stream()
                         .map(RecipeMapper::toDTO)
                         .collect(Collectors.toList());
    }

    public List<Recipe> findByOwnerId(String id) {
        return recipeRepo.findByOwnerId(id);
    }
}