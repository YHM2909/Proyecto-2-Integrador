package com.example.plataforma_cerebritos.repository;
import com.example.plataforma_cerebritos.models.CursoResultadoSimulacro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoResultadoSimulacroRepository extends JpaRepository<CursoResultadoSimulacro, Integer> {
    List<CursoResultadoSimulacro> findByIdEvaluacionSimulacro(int idevaluacion);
}
