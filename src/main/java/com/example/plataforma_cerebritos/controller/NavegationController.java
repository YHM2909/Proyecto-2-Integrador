package com.example.plataforma_cerebritos.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class NavegationController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tstamp", LocalDateTime.now());
        return "login";
    }
    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String usuario = (String) session.getAttribute("usuario");
        String nombre = (String) session.getAttribute("nombre");
        // Haz algo con los valores de usuario y nombre
        model.addAttribute("usuario", usuario);
        return "dashboard";
    }

    @GetMapping("/cursos")
    public String cursos(Model model) {
        return "cursos";
    }
}
