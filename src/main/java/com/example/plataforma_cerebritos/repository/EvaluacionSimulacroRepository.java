package com.example.plataforma_cerebritos.repository;

import com.example.plataforma_cerebritos.models.EvaluacionSimulacro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface EvaluacionSimulacroRepository extends JpaRepository<EvaluacionSimulacro, Integer> {
    List<EvaluacionSimulacro> findByidAlumno(int idAlumno);
}
