package com.example.plataforma_cerebritos.models;
import jakarta.persistence.*;
@Entity
@Table(name = "resultadopreguntacurso")
public class ResultadoPreguntaCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_evaluacioncurso")
    private int idEvaluacionCurso;

    @Column(name = "id_pregunta")
    private int idPregunta;

    @Column(name = "calificacion")
    private double calificacion;

    @Column(name = "estado")
    private int estado;

    // Constructor, getters y setters

    public ResultadoPreguntaCurso() {
    }

    public ResultadoPreguntaCurso(int idEvaluacionCurso, int idPregunta, double calificacion, int estado) {
        this.idEvaluacionCurso = idEvaluacionCurso;
        this.idPregunta = idPregunta;
        this.calificacion = calificacion;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEvaluacionCurso() {
        return idEvaluacionCurso;
    }

    public void setIdEvaluacionCurso(int idEvaluacionCurso) {
        this.idEvaluacionCurso = idEvaluacionCurso;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
