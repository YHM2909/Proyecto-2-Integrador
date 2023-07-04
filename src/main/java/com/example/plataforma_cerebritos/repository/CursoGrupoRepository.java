package com.example.plataforma_cerebritos.repository;

import com.example.plataforma_cerebritos.models.CursoGrupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoGrupoRepository extends JpaRepository<CursoGrupo, Integer> {
    List<CursoGrupo> findByUniversidadAndAlumno(Integer idUniversidad, Integer idAlumno);
}
