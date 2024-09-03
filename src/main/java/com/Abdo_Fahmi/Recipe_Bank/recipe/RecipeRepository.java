package com.Abdo_Fahmi.Recipe_Bank.recipe;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RecipeRepository extends MongoRepository<Recipe, String> {
    @Query("{ 'tags': { $in: ?0 } }")
    List<Recipe> findByTagsIn(List<String> tag);

    @Query("{ 'ingredients.name': { $in: ?0 } }")
    List<Recipe> findByIngredientsIn(List<String> ingredient);

    List<Recipe> findByOwnerId(String id);
}
