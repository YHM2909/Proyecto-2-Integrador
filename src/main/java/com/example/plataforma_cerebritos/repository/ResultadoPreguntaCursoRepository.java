package com.example.plataforma_cerebritos.repository;

import com.example.plataforma_cerebritos.models.ResultadoPreguntaCurso;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultadoPreguntaCursoRepository extends CrudRepository<ResultadoPreguntaCurso, Integer> {
    // Aquí puedes agregar métodos adicionales según tus necesidades
    List<ResultadoPreguntaCurso> findByIdEvaluacionCurso(int idEvaluacionCurso);

}
