package com.example.homworkwebapp.controllers;

import com.example.homworkwebapp.model.Ingredient;
import com.example.homworkwebapp.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ингредиенты", description = "работа с ингредиентами")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }
    @PostMapping
    @Operation(summary = "добавление ингредиента", description = "в случае успешного добавления, " +
            "возвращает добавленный объект")
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
        return ResponseEntity.ok(ingredientService.addIngredient(ingredient));
    }

    @GetMapping("/{id}")
    @Operation(summary = "поиск ингредиента по id")
    public ResponseEntity<Ingredient> getRecipeById(@PathVariable Long id) {
        return ResponseEntity.of(ingredientService.getIngredient(id));
    }

    @GetMapping("/")
    @Operation(summary = "получение списка всех нигредиентов")
    public ResponseEntity<Map<Long, Ingredient>> getAllIngredient() {
        return ResponseEntity.ok(ingredientService.getAllIngredient());
    }

    @PutMapping("/{id}")
    @Operation(summary = "редактирование ингредиента", description = "в случае успешного добавления, " +
            "возвращает измененный объект")
    public ResponseEntity<Ingredient> editIngredient(@PathVariable Long id, @RequestBody Ingredient ingredient) {
        Ingredient ingredient1 = ingredientService.editIngredient(id, ingredient);
        if (ingredient1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient1);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "удаление ингредиента", description = "в случае успешного удаления, " +
            "возвращает удаленный объект")
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable Long id) {
        Ingredient ingredient = ingredientService.deleteIngredient(id);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }


}
