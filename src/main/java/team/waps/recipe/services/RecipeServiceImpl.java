package team.waps.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.waps.recipe.models.Recipe;
import team.waps.recipe.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("Im in the service - getRecipe");
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long l) {
        log.debug("Im in the service - findById");
        Optional<Recipe> optionalRecipe = recipeRepository.findById(l);

        if (!optionalRecipe.isPresent()) {
            throw new RuntimeException("Recipe not found!");
        }
        return optionalRecipe.get();
    }

}
