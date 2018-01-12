package team.waps.recipe.services;

import team.waps.recipe.models.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long l);
}
