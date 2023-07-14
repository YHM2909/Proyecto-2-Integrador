package com.example.plataforma_cerebritos.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "evaluacioncurso")
public class EvaluacionCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_alumno")
    private Integer idAlumno;

    @Column(name = "id_curso")
    private Integer idCurso;
    @Column(name = "nota")
    private Double nota;
    @Column(name = "fecha")
    private LocalDateTime fecha;

    public EvaluacionCurso(){

    }
    public EvaluacionCurso(Integer id, Integer idAlumno, Integer idCurso, Double nota, LocalDateTime fecha) {
        this.id = id;
        this.idAlumno = idAlumno;
        this.idCurso = idCurso;
        this.nota = nota;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
