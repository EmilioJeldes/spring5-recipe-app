package team.waps.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import team.waps.recipe.commands.RecipeCommand;
import team.waps.recipe.models.*;

import static org.junit.Assert.*;

public class RecipeToRecipeCommandTest {

    private static final Long RECIPE_ID = 1l;
    private static final Integer COOK_TIME = Integer.valueOf("5");
    private static final String DESCRIPTION = "description";
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
    private static final Byte[] IMAGE = {Byte.valueOf("1"), Byte.valueOf("2")};

    RecipeToRecipeCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new RecipeToRecipeCommand(new CategoryToCategoryCommand(), new IngredientToIngredientCommand(new
                UnitOfMeasureToUnitOfMeasureCommand()), new NotesToNotesCommand());
    }

    @Test
    public void testNullConverter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyConverter() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    public void testConvert() {
        Notes notes = new Notes();
        notes.setId(NOTES_ID);

        Category category1 = new Category();
        Category category2 = new Category();
        category1.setId(CAT_ID1);
        category2.setId(CAT_ID2);

        Ingredient ingredient1 = new Ingredient();
        Ingredient ingredient2 = new Ingredient();
        ingredient1.setId(ING_ID1);
        ingredient2.setId(ING_ID2);

        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setCookTime(COOK_TIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setDirections(DIRECTIONS);
        recipe.setPrepTime(PREP_TIME);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        recipe.setNotes(notes);
        recipe.getCategories().add(category1);
        recipe.getCategories().add(category2);
        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.setImage(IMAGE);

        // when
        RecipeCommand command = converter.convert(recipe);

        // then
        assertNotNull(command);
        assertEquals(RECIPE_ID, command.getId());
        assertEquals(COOK_TIME, command.getCookTime());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(DIFFICULTY, command.getDifficulty());
        assertEquals(DIRECTIONS, command.getDirections());
        assertEquals(PREP_TIME, command.getPrepTime());
        assertEquals(SERVINGS, command.getServings());
        assertEquals(SOURCE, command.getSource());
        assertEquals(URL, command.getUrl());
        assertEquals(NOTES_ID, command.getNotes().getId());
        assertEquals(IMAGE[0], command.getImage()[0]);
        assertEquals(IMAGE[1], command.getImage()[1]);
        assertEquals(2, command.getCategories().size());
        assertEquals(2, command.getIngredients().size());
    }
}