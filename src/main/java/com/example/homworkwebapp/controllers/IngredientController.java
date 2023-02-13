package com.example.homworkwebapp.controllers;

import com.example.homworkwebapp.model.Ingredient;
import com.example.homworkwebapp.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @GetMapping("/all")
    public ResponseEntity<Map<Long, Ingredient>> getAllIngredient() {
        return ResponseEntity.ok(ingredientService.getAllIngredient());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> editIngredient(@PathVariable Long id, @RequestBody Ingredient ingredient) {
        Ingredient ingredient1 = ingredientService.editIngredient(id, ingredient);
        if (ingredient1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable Long id) {
        Ingredient ingredient = ingredientService.deleteIngredient(id);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }


}
