package com.example.plataforma_cerebritos.repository;

import com.example.plataforma_cerebritos.models.EvaluacionCurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluacionCursoRepository extends JpaRepository<EvaluacionCurso, Integer> {
    List<EvaluacionCurso> findByidAlumnoAndIdCurso(Integer idAlumno, Integer idCurso);
}
