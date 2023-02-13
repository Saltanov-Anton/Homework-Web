package com.example.homworkwebapp.service.impl;

import com.example.homworkwebapp.exception.ValidationException;
import com.example.homworkwebapp.model.Recipe;
import com.example.homworkwebapp.service.RecipeService;
import com.example.homworkwebapp.service.ValidationService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static long count = 1;
    private final Map<Long, Recipe> recipes = new TreeMap<>();
    private final ValidationService validationService;

    public RecipeServiceImpl(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        if (!validationService.validation(recipe)) {
            throw new ValidationException("Есть не заполненные поля");
        }
        recipes.put(count++, recipe);
        return recipe;
    }

    @Override
    public Optional<Recipe> getRecipe(Long num) {
        return Optional.of(recipes.get(num));
    }

    @Override
    public Map<Long, Recipe> getAllRecipe() {
        return recipes;
    }

    @Override
    public Recipe editRecipe(Long id, Recipe recipe) {
        if (recipes.containsKey(id)) {
            recipes.put(id, recipe);
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
}
