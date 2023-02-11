package com.example.homworkwebapp.service.impl;

import com.example.homworkwebapp.model.Ingredient;
import com.example.homworkwebapp.model.Recipe;
import com.example.homworkwebapp.service.ValidationService;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {


    @Override
    public boolean validation(Recipe recipe) {
        return recipe != null
                && recipe.getName() != null
                && recipe.getCookingTime() > 0
                && !recipe.getIngredients().isEmpty()
                && recipe.getSteps() != null;
    }

    @Override
    public boolean validation(Ingredient ingredient) {
        return ingredient != null
                && ingredient.getName() != null
                && ingredient.getQuantity() >= 0
                && ingredient.getUnit() != null;
    }
}
