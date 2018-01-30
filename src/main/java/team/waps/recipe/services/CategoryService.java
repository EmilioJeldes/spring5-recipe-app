package team.waps.recipe.services;

import team.waps.recipe.commands.CategoryCommand;

import java.util.Set;

public interface CategoryService {

    Set<CategoryCommand> listAllCategories();
}
