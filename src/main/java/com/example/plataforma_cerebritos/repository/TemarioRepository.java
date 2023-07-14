package com.example.plataforma_cerebritos.repository;

import com.example.plataforma_cerebritos.models.Temario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemarioRepository extends JpaRepository<Temario, Integer> {
    List<Temario> findByIdCurso(Integer idCurso);
}