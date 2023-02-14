package com.example.homworkwebapp.controllers;

import com.example.homworkwebapp.model.Recipe;
import com.example.homworkwebapp.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепты", description = "работа с рецептами")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @PostMapping
    @Operation(summary = "добавление рецепта", description = "в случае успешного добавления, " +
            "возвращает добавленный объект")
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(recipe);
    }
    @GetMapping("/{id}")
    @Operation(summary = "поиск рецепта по id")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        return ResponseEntity.of(recipeService.getRecipe(id));
    }

    @GetMapping("/")
    @Operation(summary = "получение списка всех рецептов")
    public ResponseEntity<Map<Long, Recipe>> getAllRecipe() {
        return ResponseEntity.ok(recipeService.getAllRecipe());
    }

    @PutMapping("/{id}")
    @Operation(summary = "редактирование рецепта", description = "в случае успешного добавления, " +
            "возвращает измененный объект")
    public ResponseEntity<Recipe> editRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        Recipe recipe1 = recipeService.editRecipe(id, recipe);
        if (recipe1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe1);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "удаление рецепта", description = "в случае успешного удаления, " +
            "возвращает удаленный объект")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable Long id) {
        Recipe recipe = recipeService.deleteRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }
}
