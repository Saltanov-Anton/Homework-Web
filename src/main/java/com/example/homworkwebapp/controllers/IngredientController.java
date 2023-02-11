package com.example.homworkwebapp.controllers;

import com.example.homworkwebapp.model.Ingredient;
import com.example.homworkwebapp.model.Recipe;
import com.example.homworkwebapp.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }
    @PostMapping
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
        return ResponseEntity.ok(ingredientService.addIngredient(ingredient));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getRecipeById(@PathVariable Long id) {
        return ResponseEntity.of(ingredientService.getIngredient(id));
    }
}
