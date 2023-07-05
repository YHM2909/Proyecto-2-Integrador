package com.example.plataforma_cerebritos.repository;

import com.example.plataforma_cerebritos.models.CursoGrupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoGrupoRepository extends JpaRepository<CursoGrupo, Integer> {
    @Query("SELECT c.nombre FROM CursoGrupo cg INNER JOIN Curso c ON cg.idCurso = c.id WHERE cg.idUniversidad = :idUniversidad")
    List<String> findCursoNombresByUniversidad(@Param("idUniversidad") Integer idUniversidad);
}
