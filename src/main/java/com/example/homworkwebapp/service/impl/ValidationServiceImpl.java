package com.example.homworkwebapp.service.impl;

import com.example.homworkwebapp.model.Ingredient;
import com.example.homworkwebapp.model.Recipe;
import com.example.homworkwebapp.service.ValidationService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {


    @Override
    public boolean validation(Recipe recipe) {
        return ObjectUtils.isNotEmpty(recipe)
                && !StringUtils.isBlank(recipe.getName())
                && recipe.getCookingTime() > 0
                && !recipe.getIngredients().isEmpty()
                && recipe.getSteps() != null;
    }

    @Override
    public boolean validation(Ingredient ingredient) {
        return ObjectUtils.isNotEmpty(ingredient)
                && !StringUtils.isBlank(ingredient.getName())
                && ingredient.getQuantity() >= 0
                && !StringUtils.isBlank(ingredient.getUnit());
    }
}
