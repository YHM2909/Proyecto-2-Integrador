package com.example.plataforma_cerebritos.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "curso")
public class Curso {
    @Id
    @Column(name = "id_curso")
    private Integer idCurso;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "curso")
    private List<CursoGrupo> grupos;

    public Curso(Integer idCurso, String nombre) {
        this.idCurso = idCurso;
        this.nombre = nombre;
    }

    public Curso(){

    }
    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
