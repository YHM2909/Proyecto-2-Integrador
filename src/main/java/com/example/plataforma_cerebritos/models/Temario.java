package com.example.plataforma_cerebritos.models;
import jakarta.persistence.*;
@Entity
@Table(name = "temario")
public class Temario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_temario")
    private Integer idTemario;

    @Column(name = "id_curso")
    private Integer idCurso;
    private String nombre;

    public Temario(){

    }
    public Temario(Integer idTemario, Integer idCurso, String nombre) {
        this.idTemario = idTemario;
        this.idCurso = idCurso;
        this.nombre = nombre;
    }

    public Temario(Integer idCurso, String nombre) {
        this.idCurso = idCurso;
        this.nombre = nombre;
    }

    // Getters y setters

    public Integer getIdTemario() {
        return idTemario;
    }

    public void setIdTemario(Integer idTemario) {
        this.idTemario = idTemario;
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