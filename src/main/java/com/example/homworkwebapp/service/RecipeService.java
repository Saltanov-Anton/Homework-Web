package com.example.homworkwebapp.service;

import com.example.homworkwebapp.model.Recipe;

import java.io.File;
import java.util.Map;
import java.util.Optional;

public interface RecipeService {

    Recipe addRecipe(Recipe recipe);

    Recipe getRecipe(Long num);

    Map<Long, Recipe> getAllRecipe();

    Recipe editRecipe(Long id, Recipe recipe);

    Recipe deleteRecipe(Long id);

    void readFromFile();

    File fileToTxt();
}
