package com.example.plataforma_cerebritos.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cursogrupo")
public class CursoGrupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_curso")
    private Integer idCurso;

    @Column(name = "id_universidad")
    private Integer idUniversidad;

    @Column(name = "cantidadpreguntas")
    private Integer cantidadPreguntas;

    @Transient // Esta anotación indica que este atributo no se mapea en la base de datos
    private List<Pregunta> preguntas;
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
        return idUniversidad;
    }


    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
    public void setIdUniversidad(Integer idUniversidad) {
        this.idUniversidad = idUniversidad;
    }

    public Integer getCantidadPreguntas() {
        return cantidadPreguntas;
    }

    public void setCantidadPreguntas(Integer cantidadPreguntas) {
        this.cantidadPreguntas = cantidadPreguntas;
    }
}
