package com.example.homworkwebapp.service;

import com.example.homworkwebapp.model.Ingredient;
import com.example.homworkwebapp.model.Recipe;

public interface ValidationService {

    boolean validation(Recipe recipe);

    boolean validation(Ingredient ingredient);
}
