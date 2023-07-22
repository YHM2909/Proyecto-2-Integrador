package com.example.plataforma_cerebritos.models;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "pregunta")
public class Pregunta {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "id_temario")
    private int idTemario;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "imagen_pregunta")
    private String imagenPregunta;

    @Column(name = "imagen_respuesta")
    private String imagenRespuesta;

    @Column(name = "id_curso")
    private int idCurso;

    @OneToMany(mappedBy = "pregunta")
    private List<RespuestaPregunta> respuestas;


    public List<RespuestaPregunta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<RespuestaPregunta> respuestas) {
        this.respuestas = respuestas;
    }

    // Constructor vacío
    public Pregunta() {
    }

    // Constructor con todos los campos
    public Pregunta(int id, int idTemario, String descripcion, String imagenPregunta, String imagenRespuesta, int idCurso) {
        this.id = id;
        this.idTemario = idTemario;
        this.descripcion = descripcion;
        this.imagenPregunta = imagenPregunta;
        this.imagenRespuesta = imagenRespuesta;
        this.idCurso = idCurso;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTemario() {
        return idTemario;
    }

    public void setIdTemario(int idTemario) {
        this.idTemario = idTemario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenPregunta() {
        return imagenPregunta;
    }

    public void setImagenPregunta(String imagenPregunta) {
        this.imagenPregunta = imagenPregunta;
    }

    public String getImagenRespuesta() {
        return imagenRespuesta;
    }

    public void setImagenRespuesta(String imagenRespuesta) {
        this.imagenRespuesta = imagenRespuesta;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }
}
