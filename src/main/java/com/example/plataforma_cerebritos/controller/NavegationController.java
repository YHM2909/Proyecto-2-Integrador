package com.example.plataforma_cerebritos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class NavegationController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tstamp", LocalDateTime.now());
        return "dashboard";
    }

    @GetMapping("/dashboard")
    public String redirectToIndex(Model model) {
        model.addAttribute("tstamp", LocalDateTime.now());
        return "dashboard";
    }
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/curso")
    public String curso(Model model) {
        return "cursos";
    }
}
