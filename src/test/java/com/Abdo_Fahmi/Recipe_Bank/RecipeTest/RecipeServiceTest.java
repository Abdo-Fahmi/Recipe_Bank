package com.Abdo_Fahmi.Recipe_Bank.RecipeTest;

import com.Abdo_Fahmi.Recipe_Bank.TestData;
import com.Abdo_Fahmi.Recipe_Bank.exception.RecipeNotFoundException;
import com.Abdo_Fahmi.Recipe_Bank.recipe.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

// We omit @SpringBootTest as it causes issues with jwtUtil bean generation
// and as we don't need the full application context for these tests
// @ExtendWith(MockitoExtension.class) is enough

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RecipeServiceTest {
    @Mock
    private RecipeRepository recipeRepo;

    @InjectMocks
    private RecipeService recipeService;

    @Test
    public void testSaveRecipe_ThenReturnDTO() {
        final String ownerId = "user1";
        final RecipeCreationRequest creationRequest = TestData.testCreationRequest();
        final Recipe recipeEntity = TestData.testEntity();

        when(recipeRepo.save(any(Recipe.class))).thenReturn(recipeEntity);

        final RecipeDTO savedRecipe = recipeService.saveRecipe(ownerId, creationRequest);

        // verifying that recipe repo's "save" method was called with a recipe entity as an argument
        verify(recipeRepo, times(1)).save(recipeEntity);

        // validating the returned recipe dto
        assertNotNull(savedRecipe);
        assertEquals(recipeEntity.getName(), savedRecipe.name());
        assertEquals(recipeEntity.getOwnerId(), savedRecipe.ownerId());
    }

    @Test
    public void testFindRecipe_WhenRecipeExists_ThenReturnDTO() {
        final RecipeDTO expectedDTO = TestData.testDTO();
        final Recipe recipeEntity = TestData.testEntity();
        final String recipeId = recipeEntity.getId();

        when(recipeRepo.findById(eq(recipeId))).thenReturn(Optional.of(recipeEntity));

        final RecipeDTO savedRecipe = recipeService.findRecipeById(recipeId);

        // verifying that recipe repo's "findById" method was called with a recipe id as an argument
        verify(recipeRepo, times(1)).findById(recipeId);

        // validating the returned recipe dto
        assertNotNull(savedRecipe);
        assertEquals(expectedDTO.name(), savedRecipe.name());
        assertEquals(expectedDTO.ownerId(), savedRecipe.ownerId());
    }

    @Test
    public void testFindRecipe_WhenRecipeNotFound_ThrowException() {
        final String recipeId = "none";

        when(recipeRepo.findById(recipeId)).thenReturn(Optional.empty());

        RecipeNotFoundException exception = assertThrows(RecipeNotFoundException.class, () -> {
            recipeService.findRecipeById(recipeId);
        });

        verify(recipeRepo, times(1)).findById(recipeId);

        assertEquals("Recipe not found", exception.getMessage());
    }

    // Made a class for the deleteById tests as it has multiple cases and reusable instances
    @Nested
    public class TestDeleteById {
        final String recipeId = "1";
        final String currentUserId = "1";
        final Recipe recipeEntity = TestData.testEntity();

        @Test
        public void whenRecipeExists_AndUserAuthorized_ThenRecipeIsDeleted() {
            recipeEntity.setId(recipeId);
            recipeEntity.setOwnerId(currentUserId);

            when(recipeRepo.findById(recipeId)).thenReturn(Optional.of(recipeEntity));

            recipeService.deleteRecipeById(recipeEntity.getId(), currentUserId);

            // verify that both the find & delete by id methods are called in teh repository
            verify(recipeRepo, times(1)).findById(recipeId);
            verify(recipeRepo, times(1)).deleteById(recipeId);
        }

        @Test
        public void whenRecipeExists_AndUserUnAuthorized_ThenExceptionThrown() {
            recipeEntity.setId(recipeId);
            final String userId = "none";

            when(recipeRepo.findById(recipeId)).thenReturn(Optional.of(recipeEntity));

            AccessDeniedException exception = assertThrows(AccessDeniedException.class, () -> {
                recipeService.deleteRecipeById(recipeEntity.getId(), userId);
            });
            // assertThrows(AccessDeniedException.class, () -> recipeService.deleteRecipeById(recipeId, currentUserId));
            verify(recipeRepo, times(1)).findById(recipeId);

            assertEquals("User is not authorized for this action", exception.getMessage());
        }

        @Test
        public void whenRecipeDoesNotExist_ThenExceptionThrown() {
            final String recipeId = "none";

            when(recipeRepo.findById(recipeId)).thenReturn(Optional.empty());

            RecipeNotFoundException exception = assertThrows(RecipeNotFoundException.class, () -> {
                recipeService.findRecipeById(recipeId);
            });

            verify(recipeRepo, times(1)).findById(recipeId);

            assertEquals("Recipe not found", exception.getMessage());
        }
    }

//    public RecipeDTO updateRecipe(String id, RecipeDTO recipeDTO, String currentUserId) {
//        Recipe recipe = recipeRepo.findById(id)
//                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));
//
//        // Check is the logged-in user has authorization to delete this recipe.
//        if (!recipe.getOwnerId().equals(currentUserId)) throw new AccessDeniedException("User is not authorized for this action");
//        Recipe updatedRecipe = RecipeMapper.toEntity(recipeDTO);
//
//        recipeRepo.save(updatedRecipe);
//        return RecipeMapper.toDTO(updatedRecipe);
//    }
    
    @Nested
    public class TestUpdateRecipe {

    }
}
