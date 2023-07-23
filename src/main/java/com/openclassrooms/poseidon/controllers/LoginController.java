package com.openclassrooms.poseidon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {


    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Assurez-vous que vous avez un template Thymeleaf nommé "login.html" pour afficher la page de connexion.
    }
}
