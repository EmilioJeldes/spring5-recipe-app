package team.waps.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import team.waps.recipe.commands.CategoryCommand;
import team.waps.recipe.commands.IngredientCommand;
import team.waps.recipe.commands.NotesCommand;
import team.waps.recipe.commands.RecipeCommand;
import team.waps.recipe.models.Difficulty;
import team.waps.recipe.models.Recipe;

import static org.junit.Assert.*;

public class RecipeCommandToRecipeTest {

    private static final Long RECIPE_ID = 1l;
    private static final Integer COOK_TIME = Integer.valueOf("5");
    private static final String DESCRIPTION = "descripcion";
    private static final Difficulty DIFFICULTY = Difficulty.EASY;
    private static final String DIRECTIONS = "directions";
    private static final Integer PREP_TIME = Integer.valueOf("20");
    private static final Integer SERVINGS = Integer.valueOf("4");
    private static final String SOURCE = "source";
    private static final String URL = "http://someurl.cl";
    private static final Long NOTES_ID = 9l;
    private static final Long CAT_ID1 = 4l;
    private static final Long CAT_ID2 = 6l;
    private static final Long ING_ID1 = 2l;
    private static final Long ING_ID2 = 6l;

    RecipeCommandToRecipe converter;

    @Before
    public void setUp() throws Exception {
        converter = new RecipeCommandToRecipe(new CategoryCommandToCategory(), new IngredientCommandToIngredient(new
            UnitOfMeasureCommandToUnitOfMeasure()), new
            NotesCommandToNotes());
    }

    @Test
    public void testNullConverter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyConverter() {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    public void testConvert() {
        // given
        NotesCommand notes = new NotesCommand();
        notes.setId(NOTES_ID);

        CategoryCommand category1 = new CategoryCommand();
        CategoryCommand category2 = new CategoryCommand();
        category1.setId(CAT_ID1);
        category2.setId(CAT_ID2);

        IngredientCommand ingredient1 = new IngredientCommand();
        IngredientCommand ingredient2 = new IngredientCommand();
        ingredient1.setId(ING_ID1);
        ingredient2.setId(ING_ID2);

        RecipeCommand command = new RecipeCommand();
        command.setId(RECIPE_ID);
        command.setCookTime(COOK_TIME);
        command.setDescription(DESCRIPTION);
        command.setDifficulty(DIFFICULTY);
        command.setDirections(DIRECTIONS);
        command.setPrepTime(PREP_TIME);
        command.setServings(SERVINGS);
        command.setSource(SOURCE);
        command.setUrl(URL);
        command.setNotes(notes);
        command.getCategories().add(category1);
        command.getCategories().add(category2);
        command.getIngredients().add(ingredient1);
        command.getIngredients().add(ingredient2);

        // when
        Recipe recipe = converter.convert(command);

        // then
        assertNotNull(recipe);
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }
}