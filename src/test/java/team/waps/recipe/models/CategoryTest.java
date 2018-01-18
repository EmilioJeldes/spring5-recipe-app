package team.waps.recipe.models;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.Lob;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class CategoryTest {

    private Category category;
    private static final String DESCRIPTION = "description";
    private static final Long ID_VALUE = 4l;

    @Before
    public void setUp() throws Exception {
        category = new Category();
    }

    @Test
    public void getId() {
        category.setId(ID_VALUE);
        assertEquals(ID_VALUE, category.getId());
    }

    @Test
    public void getDescription() {
        category.setDescription(DESCRIPTION);
        assertEquals(DESCRIPTION, category.getDescription());
    }

    @Test
    public void getRecipes() {
        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();
        Recipe recipe3 = new Recipe();
        recipe1.setId(1l);
        recipe2.setId(2l);
        recipe3.setId(3l);
        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(recipe1);
        recipeSet.add(recipe2);
        recipeSet.add(recipe3);
        category.setRecipes(recipeSet);
        assertNotNull(category);
        assertEquals(3, category.getRecipes().size());
    }
}