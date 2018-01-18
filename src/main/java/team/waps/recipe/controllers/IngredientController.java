package team.waps.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.waps.recipe.commands.IngredientCommand;
import team.waps.recipe.services.IngredientService;
import team.waps.recipe.services.RecipeService;
import team.waps.recipe.services.UnitOfMeasureService;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    @Autowired
    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService
        unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable("recipeId") String recipeId, Model model) {
        log.debug("Getting ingredients for recipe id: " + recipeId);

        // use command object to avoid lazy load errors in thymeleaf
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable("recipeId") String recipeId, @PathVariable("id") String id, Model model) {

        log.debug("Getting ingredient from recipe ID: " + recipeId + " ingredient ID: " + id);
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
        return "recipe/ingredient/show";

    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable("recipeId") String recipeId,
                                         @PathVariable("id") String id, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping
    @RequestMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
        IngredientCommand saveIngredientCommand = ingredientService.saveIngredientCommand(command);
        log.debug("Saved recipe id: " + command.getRecipeId());
        log.debug("Saved ingredient id: " + command.getId());
        return "redirect:/recipe/" + saveIngredientCommand.getRecipeId() + "/ingredient/" + saveIngredientCommand.getId() + "/show";
    }
}

