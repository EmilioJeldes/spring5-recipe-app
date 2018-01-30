package team.waps.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.waps.recipe.commands.CategoryCommand;
import team.waps.recipe.converters.CategoryToCategoryCommand;
import team.waps.recipe.repositories.CategoryRepository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryToCategoryCommand categoryCommand) {
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryCommand = categoryCommand;
    }

    @Override
    public Set<CategoryCommand> listAllCategories() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .map(categoryToCategoryCommand::convert)
                .collect(Collectors.toSet());
    }
}
