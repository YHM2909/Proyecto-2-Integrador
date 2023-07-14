package com.example.plataforma_cerebritos.repository;

import com.example.plataforma_cerebritos.models.RespuestaPregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespuestaPreguntaRepository extends JpaRepository<RespuestaPregunta, Integer> {

    List<RespuestaPregunta> findByPreguntaId(Integer preguntaId);

}
