package repository;

import model.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RecipeRepository extends MongoRepository<Recipe, String> {

    // TODO understand query tag better
    @Query("{ 'tags': { $in: ?0 } }")
    List<Recipe> findByTagsIn(List<String> tag);

    @Query("{ 'ingredients.name': { $in: ?0 } }")
    List<Recipe> findByIngredientsIn(List<String> ingredient);

    List<Recipe> findByOwnerId(String id);
}
