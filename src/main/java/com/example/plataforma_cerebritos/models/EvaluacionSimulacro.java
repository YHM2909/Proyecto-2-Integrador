package com.example.plataforma_cerebritos.models;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "evaluacionsimulacro")
public class EvaluacionSimulacro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_alumno")
    private Integer idAlumno;

    @Column(name = "nota")
    private Double nota;
    @Column(name = "fecha")
    private LocalDateTime fecha;

    // Constructor, getters y setters

    public EvaluacionSimulacro() {
    }

    public EvaluacionSimulacro(int id_alumno, double nota, LocalDateTime fecha) {
        this.idAlumno = id_alumno;
        this.nota = nota;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int id_alumno) {
        this.idAlumno = id_alumno;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
