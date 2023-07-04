package com.example.plataforma_cerebritos.repository;

import com.example.plataforma_cerebritos.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {
    // Puedes agregar métodos personalizados si necesitas buscar cursos por algún otro criterio
}
