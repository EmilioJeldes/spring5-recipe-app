package team.waps.recipe.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import team.waps.recipe.models.*;
import team.waps.recipe.repositories.CategoryRepository;
import team.waps.recipe.repositories.RecipeRepository;
import team.waps.recipe.repositories.UnitOfMeasureRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private RecipeRepository recipeRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                           RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        recipeRepository.saveAll(getRecipes());
        log.debug("Initializing data from bootstrap class");
    }

    private List<Recipe> getRecipes() {

        List<Recipe> recipes = new ArrayList<>(2);

        // GET UNIT OF MEASURES
        Optional<UnitOfMeasure> optionalEach = unitOfMeasureRepository.findByDescription("Each");
        if (!optionalEach.isPresent()) {
            throw new RuntimeException("Expected Unit Of Measure Not Found");
        }

        Optional<UnitOfMeasure> optionalTeaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");
        if (!optionalTeaspoon.isPresent()) {
            throw new RuntimeException("Expected Unit Of Measure Not Found");
        }

        Optional<UnitOfMeasure> optionalTablespoon = unitOfMeasureRepository.findByDescription("Tablespoon");
        if (!optionalTablespoon.isPresent()) {
            throw new RuntimeException("Expected Unit Of Measure Not Found");
        }

        Optional<UnitOfMeasure> optionalDash = unitOfMeasureRepository.findByDescription("Dash");
        if (!optionalDash.isPresent()) {
            throw new RuntimeException("Expected Unit Of Measure Not Found");
        }

        // SET OPTIONALS
        UnitOfMeasure teaspoonUom = optionalTeaspoon.get();
        UnitOfMeasure tableSpoonUom = optionalTablespoon.get();
        UnitOfMeasure dashUom = optionalDash.get();
        UnitOfMeasure eachUom = optionalEach.get();

        // GET CATEGORIES
        Optional<Category> optionalAmericanCat = categoryRepository.findByDescription("American");
        if (!optionalAmericanCat.isPresent()) {
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> optionalMexicanCat = categoryRepository.findByDescription("Mexican");
        if (!optionalMexicanCat.isPresent()) {
            throw new RuntimeException("Expected Category Not Found");
        }

        // SET OPTIONALS
        Category americanCat = optionalAmericanCat.get();
        Category mexicanCat = optionalMexicanCat.get();

        // GUACAMOLE RECIPE
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.getCategories().add(americanCat);
        guacRecipe.getCategories().add(mexicanCat);
        guacRecipe.setCookTime(0);
        guacRecipe.setPrepTime(10);
        guacRecipe.setServings(4);
        guacRecipe.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the " +
            "inside of the avocado with a blunt knife and scoop out the flesh with a spoon." + "\n" +
            "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a " +
            "little chunky.)" + "\n" +
            "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime" +
            " juice will provide some balance to the richness of the avocado and will help delay the avocados from " +
            "turning brown.\n" +
            "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their " +
            "hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of " +
            "hotness.\n" +
            "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start " +
            "with this recipe and adjust to your taste.\n" +
            "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz53kG4dXio\n" +
            "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and" +
            " to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown" +
            ".) Refrigerate until ready to serve.\n" +
            "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it " +
            "just before serving.\n" +
            "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz53kGPK2dq\n");
        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your " +
            "mashed " +
            "avocados.\n" +
            "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it" +
            " (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
            "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability " +
            "of other ingredients stop you from making guacamole.\n" +
            "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. " +
            "Purists may be horrified, but so what? It tastes great.\n");
        guacRecipe.setNotes(guacNotes);
        guacRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUom));
        guacRecipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal(.5), teaspoonUom));
        guacRecipe.addIngredient(new Ingredient("of fresh lime juice or lemon juice", new BigDecimal(1), tableSpoonUom));
        guacRecipe.addIngredient(new Ingredient("of minced red onion or thinly sliced green onion", new BigDecimal(2), tableSpoonUom));
        guacRecipe.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachUom));
        guacRecipe.addIngredient(new Ingredient("cilantro (leaves and tender stems), finely chopped", new BigDecimal(2), tableSpoonUom));
        guacRecipe.addIngredient(new Ingredient("of freshly grated black pepper", new BigDecimal(1), dashUom));
        guacRecipe.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(.5), eachUom));

        recipes.add(guacRecipe);

        // SPICY CHICKEN
        Recipe spicyRecipe = new Recipe();
        spicyRecipe.setDifficulty(Difficulty.MODERATE);
        spicyRecipe.setDescription("Spicy Chicken");
        spicyRecipe.getCategories().add(americanCat);
        spicyRecipe.getCategories().add(mexicanCat);
        spicyRecipe.setCookTime(15);
        spicyRecipe.setPrepTime(20);
        spicyRecipe.setServings(6);
        spicyRecipe.setUrl("http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        spicyRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
            "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, " +
            "cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste" +
            ". Add the chicken to the bowl and toss to coat all over.\n" +
            "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n3 Grill the " +
            "chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the " +
            "thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
            "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. " +
            "As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for " +
            "a few seconds on the other side.\n" +
            "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
            "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula." +
            " Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned" +
            " sour cream. Serve with lime wedges.\n");
        Notes spicyNotes = new Notes();
        spicyNotes.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on" +
            " buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the" +
            " cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)\n");
        spicyRecipe.setNotes(spicyNotes);
        spicyRecipe.addIngredient(new Ingredient("ancho chili powder", new BigDecimal(2), tableSpoonUom));
        spicyRecipe.addIngredient(new Ingredient("dried oregano", new BigDecimal(1), teaspoonUom));
        spicyRecipe.addIngredient(new Ingredient("dried cumin", new BigDecimal(1), teaspoonUom));
        spicyRecipe.addIngredient(new Ingredient("sugar", new BigDecimal(1), teaspoonUom));
        spicyRecipe.addIngredient(new Ingredient("salt", new BigDecimal(0.5), teaspoonUom));
        spicyRecipe.addIngredient(new Ingredient("clove garlic, finely chopped", new BigDecimal(1), eachUom));
        spicyRecipe.addIngredient(new Ingredient("finely grated orange zest", new BigDecimal(1), tableSpoonUom));
        spicyRecipe.addIngredient(new Ingredient("orange juice", new BigDecimal(3), tableSpoonUom));
        spicyRecipe.addIngredient(new Ingredient("olive oil", new BigDecimal(2), tableSpoonUom));
        spicyRecipe.addIngredient(new Ingredient("boneless chicken thighs", new BigDecimal(6), eachUom));

        recipes.add(spicyRecipe);

        return recipes;
    }

}
