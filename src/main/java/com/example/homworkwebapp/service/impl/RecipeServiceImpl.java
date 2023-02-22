package com.example.homworkwebapp.service.impl;

import com.example.homworkwebapp.exception.ValidationException;
import com.example.homworkwebapp.model.Ingredient;
import com.example.homworkwebapp.model.Recipe;
import com.example.homworkwebapp.service.FileService;
import com.example.homworkwebapp.service.IngredientService;
import com.example.homworkwebapp.service.RecipeService;
import com.example.homworkwebapp.service.ValidationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static long count = 1;
    private TreeMap<Long, Recipe> recipes = new TreeMap<>();
    private final ValidationService validationService;
    private final FileService fileService;
    @Value("${name.of.recipe.file}")
    private String dataFileName;

    @Value("${name.of.recipe.txt.file}")
    private String dataTxtFileName;

    private final IngredientService ingredientService;

    public RecipeServiceImpl(ValidationService validationService, FileService fileService, IngredientService ingredientService) {
        this.validationService = validationService;
        this.fileService = fileService;
        this.ingredientService = ingredientService;
    }
    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        if (!validationService.validation(recipe)) {
            throw new ValidationException("Есть незаполненные поля");
        }
        while (recipes.containsKey(count)) {
            count++;
        }
        recipes.put(count++, recipe);
        saveToFile();
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredientService.addIngredient(ingredient);
        }
        return recipe;
    }

    @Override
    public Recipe getRecipe(Long num) {
        return recipes.get(num);
    }

    @Override
    public Map<Long, Recipe> getAllRecipe() {
        return recipes;
    }

    @Override
    public Recipe editRecipe(Long id, Recipe recipe) {
        if (!validationService.validation(recipe)) {
            throw new ValidationException("Есть незаполненные поля");
        }
        if (recipes.containsKey(id)) {
            recipes.put(id, recipe);
            saveToFile();
            return recipe;
        }
        return null;
    }

    @Override
    public Recipe deleteRecipe(Long id) {
        Recipe recipe = null;
        if (recipes.containsKey(id)) {
            recipe = recipes.remove(id);
            saveToFile();
            init();
        }
        return recipe;
    }

    public void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            fileService.saveToFile(json, dataFileName);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readFromFile() {
        try {
            String json = fileService.readFromFile(dataFileName);
            recipes = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @Override
    public File fileToTxt() {
        return fileService.saveToFile(recipeToString(), dataTxtFileName);
    }

    public String recipeToString() {
        StringBuilder sb = new StringBuilder();

        for (Recipe recipe : recipes.values()) {
            sb.append(recipe.getName()).append("\n");
            sb.append("Время приготовления - " + recipe.getCookingTime() + "\n");
            sb.append("Ингредиенты: " + "\n");

            int count = 1;
            for (Ingredient ingredient : recipe.getIngredients()) {
                sb.append(count++ + " ").append(ingredient.getName())
                        .append(" - " + ingredient.getQuantity() + ingredient.getUnit() + "\n");
            }

            sb.append("Шаги приготовления" + "\n");
            int countSteps = 1;
            for (String steps : recipe.getSteps()) {
                sb.append(countSteps++ + " ").append(steps + "\n");
            }
        }
        return sb.toString();
    }
}
