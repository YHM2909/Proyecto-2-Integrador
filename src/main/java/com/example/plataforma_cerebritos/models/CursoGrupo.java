package com.example.plataforma_cerebritos.models;

import jakarta.persistence.*;

@Entity
@Table(name = "cursogrupo")
public class CursoGrupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_curso")
    private Integer idCurso;

    @Column(name = "id_universidad")
    private Integer universidad;

    @Column(name = "cantidadpreguntas")
    private Integer cantidadPreguntas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public Integer getIdUniversidad() {
        return universidad;
    }

    public void setIdUniversidad(Integer idUniversidad) {
        this.universidad = idUniversidad;
    }

    public Integer getCantidadPreguntas() {
        return cantidadPreguntas;
    }

    public void setCantidadPreguntas(Integer cantidadPreguntas) {
        this.cantidadPreguntas = cantidadPreguntas;
    }
}
