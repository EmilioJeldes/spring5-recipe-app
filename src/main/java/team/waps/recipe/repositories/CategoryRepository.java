package team.waps.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import team.waps.recipe.models.Category;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);
}
