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

    private IngredientService ingredientService;

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
            throw new ValidationException("Есть не заполненные поля");
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
        if (recipes.containsKey(id)) {
            recipes.put(id, recipe);
            saveToFile();
            return recipe;
        }
        return null;
    }

    @Override
    public Recipe deleteRecipe(Long id) {
        if (recipes.containsKey(id)) {
            return recipes.remove(id);
        }
        return null;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            fileService.saveToFile(json, dataFileName);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        try {
            String json = fileService.readFromFile(dataFileName);
            recipes = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
