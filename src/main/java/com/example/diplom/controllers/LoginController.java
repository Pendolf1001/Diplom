package com.example.diplom.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    /**
     * Отображает страницу входа.
     *
     * @return Шаблон страницы входа (login.html)
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Отображает страницу выхода.
     *
     * @return Шаблон страницы выхода (logout.html)
     */
    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }
}