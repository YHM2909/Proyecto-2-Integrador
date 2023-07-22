package com.example.plataforma_cerebritos.models;
import jakarta.persistence.*;

@Entity
@Table(name = "respuestapregunta")
public class RespuestaPregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_pregunta")
    private Pregunta pregunta;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estado")
    private Integer estado;

    // Constructor vacío
    public RespuestaPregunta() {
    }

    public RespuestaPregunta(Integer id, Pregunta pregunta, String descripcion, Integer estado) {
        this.id = id;
        this.pregunta = pregunta;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}
