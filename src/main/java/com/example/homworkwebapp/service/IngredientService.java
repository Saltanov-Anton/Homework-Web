package com.example.homworkwebapp.service;

import com.example.homworkwebapp.model.Ingredient;

import java.util.Optional;

public interface IngredientService {

    Ingredient addIngredient(Ingredient ingredient);

    Optional<Ingredient> getIngredient(Long num);
}
