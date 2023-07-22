package com.example.plataforma_cerebritos.repository;

import com.example.plataforma_cerebritos.models.ResultadoPreguntaSimulacro;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultadoPreguntaSimulacroRepository extends CrudRepository<ResultadoPreguntaSimulacro, Integer> {
    List<ResultadoPreguntaSimulacro> findByidEvaluacionSimulacro(int idEvaluacionSimulacro);

}
