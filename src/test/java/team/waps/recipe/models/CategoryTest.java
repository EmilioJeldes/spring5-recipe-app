package team.waps.recipe.models;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.Lob;

import static org.junit.Assert.*;

public class CategoryTest {

    private Category category;

    @Before
    public void setUp() throws Exception {
        category = new Category();
    }

    @Test
    public void getId() {
        Long idValue = 4l;
        category.setId(4l);
        assertEquals(idValue, category.getId());
    }

    @Test
    public void getDescription() {
    }

    @Test
    public void getRecipes() {
    }
}