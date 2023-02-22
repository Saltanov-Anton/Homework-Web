package com.example.homworkwebapp.controllers;

import com.example.homworkwebapp.service.FileService;
import com.example.homworkwebapp.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files")
@Tag(name = "Файлы рецептов и ингредиентов", description = "загрузка файлов")
public class FileController {

    @Value("${name.of.recipe.file}")
    private String dataRecipeFileName;

    @Value("${name.of.ingredient.file}")
    private String dataIngredientFileName;

    RecipeService recipeService;
    FileService fileService;

    public FileController(RecipeService recipeService, FileService fileService) {
        this.recipeService = recipeService;
        this.fileService = fileService;
    }

    @GetMapping("recipe/export")
    @Operation(summary = "Выгрузка файлов")
    public ResponseEntity<InputStreamResource> downloadRecipeFile() throws FileNotFoundException {
        File recipeFile = fileService.getFile(dataRecipeFileName);
        if (recipeFile.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(recipeFile));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(recipeFile.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + recipeFile.getName())
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("recipe/export/txt")
    @Operation(summary = "Выгрузка файлов")
    public ResponseEntity<InputStreamResource> downloadRecipeFileTxt() throws FileNotFoundException {
        File recipeFile = recipeService.fileToTxt();
        if (recipeFile.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(recipeFile));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(recipeFile.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + recipeFile.getName())
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/recipe/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка файлов с рецептами")
    public ResponseEntity<Void> uploadRecipeFile(@RequestParam MultipartFile file) {
        fileService.clearFile();
        File recipeFile = fileService.getFile(dataRecipeFileName);
        try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(recipeFile));
            BufferedInputStream bis = new BufferedInputStream(file.getInputStream())) {
            bis.transferTo(bos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping(value = "/ingredient/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка файлов с ингредиентами")
    public ResponseEntity<Void> uploadIngredientFile(@RequestParam MultipartFile file) {
        fileService.clearFile();
        File ingredientFile = fileService.getFile(dataIngredientFileName);
        try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(ingredientFile));
            BufferedInputStream bis = new BufferedInputStream(file.getInputStream())) {
            bis.transferTo(bos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
