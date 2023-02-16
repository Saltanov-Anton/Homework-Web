package com.example.homworkwebapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ingredient {

    private String name;
    private int quantity;
    private String unit;

}
