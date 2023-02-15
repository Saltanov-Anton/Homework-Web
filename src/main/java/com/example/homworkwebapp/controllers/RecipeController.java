package com.example.homworkwebapp.controllers;

import com.example.homworkwebapp.exception.ValidationException;
import com.example.homworkwebapp.model.Recipe;
import com.example.homworkwebapp.service.RecipeService;
import com.example.homworkwebapp.service.ValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепты", description = "работа с рецептами")
public class RecipeController {

    private final RecipeService recipeService;
    private final ValidationService validationService;

    public RecipeController(RecipeService recipeService, ValidationService validationService) {
        this.recipeService = recipeService;
        this.validationService = validationService;
    }

    @PostMapping
    @Operation(summary = "добавление рецепта", description = "в случае успешного добавления, " +
            "возвращает добавленный объект")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт добавлен"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Есть не заполненные поля"
            )
    }
    )
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        try {
            recipeService.addRecipe(recipe);
        } catch (ValidationException e) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/{id}")
    @Operation(summary = "поиск рецепта по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт найден"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт не найден"
            )
    }
    )
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        if (ObjectUtils.isEmpty(recipeService.getRecipe(id))) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(recipeService.getRecipe(id));
    }

    @GetMapping("/")
    @Operation(summary = "получение списка всех рецептов")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепты найдены"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепты не найдены"
            )
    }
    )
    public ResponseEntity<Map<Long, Recipe>> getAllRecipe() {
        if (ObjectUtils.isEmpty(recipeService.getAllRecipe())) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(recipeService.getAllRecipe());
    }

    @PutMapping("/{id}")
    @Operation(summary = "редактирование рецепта", description = "в случае успешного добавления, " +
            "возвращает измененный объект")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "Есть не заполненные поля, у передоваемого рецепта"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепты не найдены"
            )
    }
    )
    public ResponseEntity<Recipe> editRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        Recipe recipe1 = recipeService.editRecipe(id, recipe);
        if (recipe1 == null) {
            return ResponseEntity.notFound().build();
        }else if (validationService.validation(recipe)) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(recipe1);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "удаление рецепта", description = "в случае успешного удаления, " +
            "возвращает удаленный объект")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт удален"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт не найден"
            )
    }
    )
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable Long id) {
        Recipe recipe = recipeService.deleteRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }
}
