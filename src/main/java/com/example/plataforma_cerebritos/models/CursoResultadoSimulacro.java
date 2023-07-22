package com.example.plataforma_cerebritos.models;
import jakarta.persistence.*;

@Entity
@Table(name = "cursoresultadosimulacro")
public class CursoResultadoSimulacro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_curso")
    private int idCurso;

    @Column(name = "id_evaluacionsimulacro")
    private int idEvaluacionSimulacro;

    @Column(name = "pcorrectas")
    private int pCorrectas;

    @Column(name = "pincorrectas")
    private int pIncorrectas;

    // Campo adicional para el nombre del curso
    @Transient
    private String nombreCurso;
    public CursoResultadoSimulacro() {
    }

    public CursoResultadoSimulacro(int idCurso, int idEvaluacionSimulacro, int pCorrectas, int pIncorrectas) {
        this.idCurso = idCurso;
        this.idEvaluacionSimulacro = idEvaluacionSimulacro;
        this.pCorrectas = pCorrectas;
        this.pIncorrectas = pIncorrectas;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdEvaluacionSimulacro() {
        return idEvaluacionSimulacro;
    }

    public void setIdEvaluacionSimulacro(int idEvaluacionSimulacro) {
        this.idEvaluacionSimulacro = idEvaluacionSimulacro;
    }

    public int getpCorrectas() {
        return pCorrectas;
    }

    public void setpCorrectas(int pCorrectas) {
        this.pCorrectas = pCorrectas;
    }

    public int getpIncorrectas() {
        return pIncorrectas;
    }

    public void setpIncorrectas(int pIncorrectas) {
        this.pIncorrectas = pIncorrectas;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }
}
