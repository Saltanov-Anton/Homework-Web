package com.example.homworkwebapp.service;

import com.example.homworkwebapp.model.Ingredient;

import java.util.Map;
import java.util.Optional;

public interface IngredientService {

    Ingredient addIngredient(Ingredient ingredient);

    Ingredient getIngredient(Long num);

    Map<Long, Ingredient> getAllIngredient();

    Ingredient editIngredient(Long id, Ingredient ingredient);

    Ingredient deleteIngredient(Long id);
}
