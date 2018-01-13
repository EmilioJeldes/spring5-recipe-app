package team.waps.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import team.waps.recipe.commands.CategoryCommand;
import team.waps.recipe.models.Category;

import static org.junit.Assert.*;

public class CategoryToCategoryCommandTest {

    private static final Long LONG_ID = 1l;
    private static final String DESCRIPTION = "Description";

    CategoryToCategoryCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    public void testNullConverter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyConverter() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    public void testConvert() {
        // given
        Category category = new Category();
        category.setId(LONG_ID);
        category.setDescription(DESCRIPTION);

        // when
        CategoryCommand comand = converter.convert(category);

        // then
        assertNotNull(comand);
        assertEquals(LONG_ID, comand.getId());
        assertEquals(DESCRIPTION, comand.getDescription());
    }
}