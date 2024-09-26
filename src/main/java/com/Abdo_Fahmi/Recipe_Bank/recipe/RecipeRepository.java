package com.Abdo_Fahmi.Recipe_Bank.recipe;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String> {
    @Query("{ 'tags': { $in: ?0 } }")
    List<Recipe> findByTagsIn(List<String> tag);

    @Query("{ 'ingredients.username': { $in: ?0 } }")
    List<Recipe> findByIngredientsIn(List<String> ingredient);

    List<Recipe> findByOwnerId(String id);
}
