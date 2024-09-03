package com.Abdo_Fahmi.Recipe_Bank.user;

import com.Abdo_Fahmi.Recipe_Bank.recipe.RecipeDTO;
import com.Abdo_Fahmi.Recipe_Bank.recipe.RecipeProxyDTO;
import com.Abdo_Fahmi.Recipe_Bank.recipe.RecipeService;
import com.Abdo_Fahmi.Recipe_Bank.security.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RecipeService recipeService;

    // TODO Redo the delete endpoint and restrict its access to admins.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update-account")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user, @AuthenticationPrincipal UserPrincipal currentUser) {
        UserDTO updatedUser = userService.updateUser(currentUser.getId(), user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PatchMapping("/update-account/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest, @AuthenticationPrincipal UserPrincipal currentUser) {
        userService.changePassword(currentUser.getId(), passwordChangeRequest);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable String id) {
        UserDTO foundUserDTO = userService.findUserById(id);
        return new ResponseEntity<>(foundUserDTO, HttpStatus.FOUND);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<UserDTO> findUserByName(@PathVariable String name) {
        UserDTO foundUserDTO = userService.findUserByName(name);
        return new ResponseEntity<>(foundUserDTO, HttpStatus.FOUND);
    }

    // Get the currently authenticated user and return recipes according the that.
    @GetMapping("/my-recipes")
    public ResponseEntity<List<RecipeProxyDTO>> getByOwnerId(@AuthenticationPrincipal UserPrincipal currentUser) {
        List<RecipeProxyDTO> foundRecipes = recipeService.findByOwnerId(currentUser.getId());
        return new ResponseEntity<>(foundRecipes, HttpStatus.FOUND);
    }

    @PostMapping("/my-favorites/{recipeId}")
    public ResponseEntity<Void> addRecipeToFavorites(@PathVariable String recipeId, @AuthenticationPrincipal UserPrincipal currentUser) {
        if (userService.addRecipeToFavorites(currentUser.getId(), recipeId))
            return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/my-favorites/{recipeId}")
    public ResponseEntity<Void> deleteRecipeFromFavorites(@PathVariable String recipeId, @AuthenticationPrincipal UserPrincipal currentUser) {
        if (userService.deleteRecipeFromFavorites(currentUser.getId(), recipeId))
            return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/my-favorites")
    public ResponseEntity<List<RecipeProxyDTO>> getUserFavorites(@AuthenticationPrincipal UserPrincipal currentUser) {
        Set<String> favoritesIDs = userService.getUserFavorites(currentUser.getId());
        List<RecipeProxyDTO> favoriteRecipes = recipeService.getRecipesById(favoritesIDs);
        return new ResponseEntity<>(favoriteRecipes, HttpStatus.FOUND);
    }
}
