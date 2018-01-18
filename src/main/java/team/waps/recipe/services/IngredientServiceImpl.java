package team.waps.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.waps.recipe.commands.IngredientCommand;
import team.waps.recipe.converters.IngredientCommandToIngredient;
import team.waps.recipe.converters.IngredientToIngredientCommand;
import team.waps.recipe.models.Ingredient;
import team.waps.recipe.models.Recipe;
import team.waps.recipe.repositories.RecipeRepository;
import team.waps.recipe.repositories.UnitOfMeasureRepository;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepository
                                     unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if (!optionalRecipe.isPresent()) {
            //todo impl error handling
            log.error("recipe id not found ID: " + recipeId);
        }
        Recipe recipe = optionalRecipe.get();

        Optional<IngredientCommand> optionalIngredientCommand = recipe.getIngredients().stream()
            .filter(ingredient -> ingredient.getId().equals(ingredientId))
            .map(ingredientToIngredientCommand::convert).findFirst();

        if (!optionalIngredientCommand.isPresent()) {
            //todo impl error handling
            log.error("Ingredient id not found ID: " + ingredientId);
        }

        return optionalIngredientCommand.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if (!recipeOptional.isPresent()) {
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setId(command.getId());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setRecipe(recipe);
                ingredientFound.setUom(unitOfMeasureRepository
                    .findById(command.getUom().getId())
                    .orElseThrow(() -> new RuntimeException("UOM Not Found")));
            } else {
                // new Ingredient
                recipe.addIngredient(ingredientCommandToIngredient.convert(command));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);
            return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst()
                .get());
        }

    }
}
