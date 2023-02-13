package com.example.homworkwebapp.controllers;

import com.example.homworkwebapp.model.Recipe;
import com.example.homworkwebapp.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @PostMapping
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(recipe);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        return ResponseEntity.of(recipeService.getRecipe(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Map<Long, Recipe>> getAllRecipe() {
        return ResponseEntity.ok(recipeService.getAllRecipe());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> editRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        Recipe recipe1 = recipeService.editRecipe(id, recipe);
        if (recipe1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable Long id) {
        Recipe recipe = recipeService.deleteRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }
}
