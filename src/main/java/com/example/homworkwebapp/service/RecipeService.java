package com.example.homworkwebapp.service;

import com.example.homworkwebapp.model.Recipe;

import java.util.Optional;

public interface RecipeService {

    Recipe addRecipe(Recipe recipe);

    Optional<Recipe> getRecipe(Long num);
}
