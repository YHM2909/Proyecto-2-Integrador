package com.example.plataforma_cerebritos.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.example.plataforma_cerebritos.models.Alumno;
import org.springframework.stereotype.Service;
import com.example.plataforma_cerebritos.repository.CredentialRepository;

@Service
public class CredentialService {
    private final CredentialRepository credentialRepository;
    public CredentialService(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }
    // Método para autenticar el usuario y contraseña
    public boolean authenticate(String usuario, String password) {
        // Convertir la contraseña a MD5
        String md5Password = convertToMD5(password);

        // Llamar al método authenticate del repositorio
        Alumno alumno = credentialRepository.authenticate(usuario, md5Password);

        // Si se encontró un alumno, se considera autenticado
        return alumno != null;
    }

    // Método privado para convertir una cadena a MD5
    private String convertToMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
