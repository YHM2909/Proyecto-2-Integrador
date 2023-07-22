package com.example.plataforma_cerebritos.models;
import jakarta.persistence.*;
@Entity
@Table(name = "resultadopreguntasimulacro")
public class ResultadoPreguntaSimulacro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_pregunta")
    private int idPregunta;

    @Column(name = "calificacion")
    private double calificacion;

    @Column(name = "estado")
    private int estado;

    @Column(name = "id_cursoresultado")
    private int idCursoResultado;

    @Column(name = "id_evaluacionsimulacro")
    private int idEvaluacionSimulacro;

    public int getIdEvaluacionSimulacro() {
        return idEvaluacionSimulacro;
    }
    @ManyToOne
    @JoinColumn(name = "id_cursoresultado", referencedColumnName = "id_curso", insertable = false, updatable = false)
    private Curso curso;

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setIdEvaluacionSimulacro(int idEvaluacionSimulacro) {
        this.idEvaluacionSimulacro = idEvaluacionSimulacro;
    }

    // Constructor por defecto
    public ResultadoPreguntaSimulacro() {
    }

    // Constructor con parámetros
    public ResultadoPreguntaSimulacro(int idPregunta, double calificacion, int estado, int idCursoResultado) {
        this.idPregunta = idPregunta;
        this.calificacion = calificacion;
        this.estado = estado;
        this.idCursoResultado = idCursoResultado;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getIdCursoResultado() {
        return idCursoResultado;
    }

    public void setIdCursoResultado(int idCursoResultado) {
        this.idCursoResultado = idCursoResultado;
    }
}
