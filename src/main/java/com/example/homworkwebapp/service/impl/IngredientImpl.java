package com.example.homworkwebapp.service.impl;

import com.example.homworkwebapp.exception.ValidationException;
import com.example.homworkwebapp.model.Ingredient;
import com.example.homworkwebapp.service.IngredientService;
import com.example.homworkwebapp.service.ValidationService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Service
public class IngredientImpl implements IngredientService {

    private static long count = 1;

    private static final Map<Long, Ingredient> ingredients = new TreeMap<>();
    private final ValidationService validationService;

    public IngredientImpl(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        if (!validationService.validation(ingredient)) {
            throw new ValidationException("Есть не заполненные поля");
        }
        ingredients.put(count, ingredient);
        return ingredient;
    }

    @Override
    public Optional<Ingredient> getIngredient(Long num) {
        return Optional.of(ingredients.get(num));
    }
}
