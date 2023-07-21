package com.example.plataforma_cerebritos.repository;
import com.example.plataforma_cerebritos.models.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Integer> {
    List<Pregunta> findByIdTemario(Integer temarioId);

    List<Pregunta> findAllByIdCurso(int idCurso);
}
