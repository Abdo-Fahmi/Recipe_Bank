package com.Abdo_Fahmi.Recipe_Bank.user;

import com.Abdo_Fahmi.Recipe_Bank.exception.*;
import com.Abdo_Fahmi.Recipe_Bank.recipe.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final RecipeRepository recipeRepo;
    private final PasswordEncoder passwordEncoder;

    public void deleteUserById(String id) {
        if (!userRepo.existsById(id)) throw new UserNotFoundException("User doesn't exist");
        userRepo.deleteById(id);
    }

    public UserDTO updateUser(String id, UserDTO user) {
        User updatedUser = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User doesn't exists"));

        // Check if the name is already taken by another user
        // The first check is to make sure the name/email are different (being changed) before we check for duplicates
        if (!updatedUser.getName().equals(user.name()) && userRepo.existsByName(user.name()))
            throw new NameAlreadyInUseException("Name is already taken by another user");

        // Check if the email is already taken by another user
        if (!updatedUser.getEmail().equals(user.email()) && userRepo.existsByEmail(user.email()))
            throw new EmailAlreadyInUseException("Email is already in use");

        // Applying changes
        updatedUser.setName(user.name());
        updatedUser.setEmail(user.email());

        updatedUser = userRepo.save(updatedUser);

        return UserMapper.toResponseDTO(updatedUser);
    }

    public void changePassword(String id, PasswordChangeRequest request) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User doesn't exists"));

        // Checking if the old password is correct
        if (!passwordEncoder.matches(request.oldPassword(), (user.getPassword())))
            throw new InvalidCredentialsException("Incorrect password");

        // Checking if the password was repeated properly
        if (!request.newPassword().equals(request.repeatPassword()))
            throw new PasswordsDoNotMatchException("Password are not the same");

        // Set new password and save new user info
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepo.save(user);
    }

    public UserDTO findUserById(String userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserMapper.toResponseDTO(user);
    }

    public UserDTO findUserByName(String name) {
        User user = userRepo.findByName(name)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserMapper.toResponseDTO(user);
    }

    public void addRecipeToFavorites(String id, String recipeId) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Checking that the chosen recipe exists.
        if (!recipeRepo.existsById(recipeId)) throw new RecipeNotFoundException("Recipe not found");

        // Checking if the list is null, in case this is the first favorite.
        // NOTE maybe it is better to initialize a set in the user entity itself rather than checking and initializing here.
        if(user.getFavorites() == null) user.setFavorites(new HashSet<>());

        // We don't check for duplicates since we are using a set to store the favored recipe IDs
        user.getFavorites().add(recipeId);
        userRepo.save(user);
    }

    public Set<String> getUserFavorites(String id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return user.getFavorites();
    }

    public void deleteRecipeFromFavorites(String id, String recipeId) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Checking if the list is empty
        if(user.getFavorites() == null) user.setFavorites(new HashSet<>());

        // Checking that the chosen recipe exists.
        if (!recipeRepo.existsById(recipeId)) throw new RecipeNotFoundException("Recipe does not exists");

        // Checking if the provided id is in the user's favorites
        if(!user.getFavorites().contains(recipeId)) throw new RecipeNotFoundException("Recipe is not in favorites");

        // We don't check for duplicates since we are using a set to store the favored recipe IDs
        user.getFavorites().remove(recipeId);
        userRepo.save(user);
    }
}
