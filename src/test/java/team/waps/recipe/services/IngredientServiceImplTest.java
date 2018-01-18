package team.waps.recipe.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import team.waps.recipe.commands.IngredientCommand;
import team.waps.recipe.converters.IngredientCommandToIngredient;
import team.waps.recipe.converters.IngredientToIngredientCommand;
import team.waps.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import team.waps.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import team.waps.recipe.models.Ingredient;
import team.waps.recipe.models.Recipe;
import team.waps.recipe.repositories.RecipeRepository;
import team.waps.recipe.repositories.UnitOfMeasureRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    private RecipeRepository recipeRepository;

    private IngredientService ingredientService;

    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand, ingredientCommandToIngredient,
            unitOfMeasureRepository);
    }

    @Test
    public void testFindByRecipeIdAndIngredientId() {
        // given
        Recipe recipe = new Recipe();
        recipe.setId(1l);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(2l);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(5l);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3l);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        // when
        IngredientCommand command = ingredientService.findByRecipeIdAndIngredientId(1l, 3l);

        // then
        assertEquals(Long.valueOf(3l), command.getId());
        assertEquals(Long.valueOf(1l), command.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testSaveIngredientCommand() {
        // given
        IngredientCommand command = new IngredientCommand();
        command.setId(3l);
        command.setRecipeId(2l);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());
        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3l);

        // when
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        // then
        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}