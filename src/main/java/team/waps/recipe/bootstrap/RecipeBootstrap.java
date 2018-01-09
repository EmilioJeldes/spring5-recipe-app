package team.waps.recipe.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import team.waps.recipe.models.Category;
import team.waps.recipe.models.Difficulty;
import team.waps.recipe.models.Recipe;
import team.waps.recipe.models.UnitOfMeasure;
import team.waps.recipe.repositories.CategoryRepository;
import team.waps.recipe.repositories.UnitOfMeasureRepository;

import java.util.Set;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initData();
    }

    private void initData() {

        Set<Category> categories = null;
        categoryRepository.findAll().forEach(category -> {
            if (category.equals("Mexican") || category.equals("Fast Food")) {
                categories.add(category);
            }
        });

        Recipe recipe = new Recipe();
        recipe.setCookTime(10);
        recipe.setDescription("The BEST guacamole! So easy to make with ripe avocados, salt, serrano chiles, cilantro" +
            " and lime. Garnish with red radishes or jicama. Serve with tortilla chips.");
        recipe.setDifficulty(Difficulty.EASY);
        recipe.setCategories(categories);
//        recipe.setIngredients();

    }
}
