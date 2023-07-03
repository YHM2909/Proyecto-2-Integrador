package com.example.plataforma_cerebritos.controller;

import com.example.plataforma_cerebritos.models.Alumno;
import com.example.plataforma_cerebritos.models.LoginRequest;
import com.example.plataforma_cerebritos.repository.CredentialRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
public class CredentialController {
    private final CredentialRepository credentialRepository;

    @Autowired
    public CredentialController(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        String passwordMD5 = convertToMD5(loginRequest.getPassword());

        Optional<Alumno> optionalAlumno = credentialRepository.findByUsuarioAndPassword(loginRequest.getUsuario(), passwordMD5);
        if (optionalAlumno.isPresent()) {
            // El usuario existe en la base de datos, puedes redirigir a la página principal o hacer alguna acción adicional
            Alumno alumno = optionalAlumno.get();
            HttpSession session = request.getSession();
            session.setAttribute("usuario", alumno.getUsuario());
            session.setAttribute("nombre", alumno.getNombres());

            return ResponseEntity.ok("Inicio de sesión exitoso");
        } else {
            // El usuario no existe o las credenciales son incorrectas, puedes mostrar un mensaje de error o redirigir a una página de inicio de sesión nuevamente
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }

    private String convertToMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Manejar el error apropiadamente o lanzar una excepción
            return null;
        }
    }
}
