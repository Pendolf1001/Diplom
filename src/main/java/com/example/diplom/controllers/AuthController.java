package com.example.diplom.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Шаблон для входа
    }

    @GetMapping("/profile")
    public String profile(Principal principal) {
        return "Profile page for: " + principal.getName();
    }
}
