package com.example.plataforma_cerebritos.controller;

import com.example.plataforma_cerebritos.service.CredentialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CredentialController {
    private final CredentialService credentialService;

    // Declaramos el constructor de credentialservice para poder acceder a todas sus funciones
    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping("/login")
    public String login(@RequestParam("usuario") String usuario, @RequestParam("password") String password, Model model) {
        if (credentialService.authenticate(usuario, password)) {
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Credenciales inválidas");
            return "login";
        }
    }
}
