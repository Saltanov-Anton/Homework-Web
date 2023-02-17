package com.example.homworkwebapp.service.impl;

import com.example.homworkwebapp.exception.ValidationException;
import com.example.homworkwebapp.model.Ingredient;
import com.example.homworkwebapp.service.FileService;
import com.example.homworkwebapp.service.IngredientService;
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
public class IngredientImpl implements IngredientService {

    private static long count = 1;

    private static Map<Long, Ingredient> ingredients = new TreeMap<>();
    private final ValidationService validationService;
    private final FileService fileService;
    @Value("${name.of.ingredient.file}")
    private String dataFileName;

    public IngredientImpl(ValidationService validationService, FileService fileService) {
        this.validationService = validationService;
        this.fileService = fileService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        if (!validationService.validation(ingredient)) {
            throw new ValidationException("Есть не заполненные поля");
        }
        while (ingredients.containsKey(count)) {
            count++;
        }
        ingredients.put(count++, ingredient);
        saveToFile();
        return ingredient;
    }

    @Override
    public Ingredient getIngredient(Long id) {
        return ingredients.get(id);
    }

    @Override
    public Map<Long, Ingredient> getAllIngredient() {
        return ingredients;
    }

    @Override
    public Ingredient editIngredient(Long id, Ingredient ingredient) {
        if (!validationService.validation(ingredient)) {
            throw new ValidationException("Есть не заполненные поля");
        }
        if (ingredients.containsKey(id)) {
            ingredients.put(id, ingredient);
            saveToFile();
            return ingredient;
        }
        return null;
    }

    @Override
    public Ingredient deleteIngredient(Long id) {
        Ingredient ingredient = null;
        if (ingredients.containsKey(id)) {
            ingredient =  ingredients.remove(id);
            saveToFile();
            init();
        }
        return ingredient;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            fileService.saveToFile(json, dataFileName);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        try {
            String json = fileService.readFromFile(dataFileName);
            ingredients = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
