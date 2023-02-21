package com.example.homworkwebapp.controllers;

import com.example.homworkwebapp.exception.ValidationException;
import com.example.homworkwebapp.model.Ingredient;
import com.example.homworkwebapp.service.IngredientService;
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
@RequestMapping("/ingredient")
@Tag(name = "Ингредиенты", description = "работа с ингредиентами")
public class IngredientController {

    private final IngredientService ingredientService;
    private final ValidationService validationService;

    public IngredientController(IngredientService ingredientService, ValidationService validationService) {
        this.ingredientService = ingredientService;
        this.validationService = validationService;
    }
    @PostMapping
    @Operation(summary = "добавление ингредиента", description = "в случае успешного добавления, " +
            "возвращает добавленный объект")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент добавлен"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Есть не заполненные поля"
            )
    }
    )
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
        try {
            return ResponseEntity.ok(ingredientService.addIngredient(ingredient));
        } catch (ValidationException e) {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "поиск ингредиента по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент найден"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент не найден"
            )
    }
    )
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable Long id) {
        if (ObjectUtils.isEmpty(ingredientService.getIngredient(id))) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(ingredientService.getIngredient(id));
    }

    @GetMapping("/")
    @Operation(summary = "получение списка всех нигредиентов")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиенты найдены"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиенты не найдены"
            )
    }
    )
    public ResponseEntity<Map<Long, Ingredient>> getAllIngredient() {
        if (ObjectUtils.isEmpty(ingredientService.getAllIngredient())) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(ingredientService.getAllIngredient());
    }

    @PutMapping("/{id}")
    @Operation(summary = "редактирование ингредиента", description = "в случае успешного добавления, " +
            "возвращает измененный объект")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "Есть не заполненные поля, у передоваемого ингредиента"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиенты не найдены"
            )
    }
    )
    public ResponseEntity<Ingredient> editIngredient(@PathVariable Long id, @RequestBody Ingredient ingredient) {
        Ingredient ingredient1 = ingredientService.editIngredient(id, ingredient);
        if (ingredient1 == null) {
            return ResponseEntity.notFound().build();
        } else if (validationService.validation(ingredient)) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(ingredient1);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "удаление ингредиента", description = "в случае успешного удаления, " +
            "возвращает удаленный объект")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент удален"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент не найден"
            )
    }
    )
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable Long id) {
        Ingredient ingredient = ingredientService.deleteIngredient(id);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }


}
