package team.waps.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import team.waps.recipe.models.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
