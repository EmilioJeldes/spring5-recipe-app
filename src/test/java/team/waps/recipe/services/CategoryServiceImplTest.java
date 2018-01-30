package team.waps.recipe.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import team.waps.recipe.commands.CategoryCommand;
import team.waps.recipe.converters.CategoryToCategoryCommand;
import team.waps.recipe.models.Category;
import team.waps.recipe.repositories.CategoryRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CategoryServiceImplTest {

    private static final String DESCRIPTION1 = "Description1";
    private static final String DESCRIPTION2 = "Description2";
    private static final String DESCRIPTION3 = "Description3";
    private static final Long LONG_ID1 = 1L;
    private static final Long LONG_ID2 = 2L;
    private static final Long LONG_ID3 = 3L;

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryService categoryService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository, new CategoryToCategoryCommand());
    }

    @Test
    public void testListAllCategories() {
        //given
        Category category1 = new Category();
        Category category2 = new Category();
        Category category3 = new Category();

        category1.setId(LONG_ID1);
        category2.setId(LONG_ID2);
        category3.setId(LONG_ID3);
        category1.setDescription(DESCRIPTION1);
        category2.setDescription(DESCRIPTION2);
        category3.setDescription(DESCRIPTION3);

        Set<Category> categories = new HashSet<>();
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);

        when(categoryRepository.findAll()).thenReturn(categories);

        // when
        Set<CategoryCommand> categoryCommands = categoryService.listAllCategories();

        // then
        assertEquals(3, categoryCommands.size());
        verify(categoryRepository, times(1)).findAll();
    }
}