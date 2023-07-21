package com.example.plataforma_cerebritos.repository;

import com.example.plataforma_cerebritos.models.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<Alumno, Integer> {
    Optional<Alumno> findByUsuarioAndPassword(String usuario, String password);
}

