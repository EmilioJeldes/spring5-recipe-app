package team.waps.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import team.waps.recipe.models.UnitOfMeasure;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    Optional<UnitOfMeasure> findByDescription(String description);
}


