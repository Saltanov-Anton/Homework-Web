package com.example.homworkwebapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    @GetMapping
    public String startPage() {
        return "Приложение запущено";
    }
    @GetMapping("/info")
    public String secondPage() {
        return "1 - Салтанов Антон <br>" +
                "2 - HomeWorkApp <br>" +
                "3 - 06.02.2023 <br>" +
                "4 - Приложение с домашками";
    }
}
