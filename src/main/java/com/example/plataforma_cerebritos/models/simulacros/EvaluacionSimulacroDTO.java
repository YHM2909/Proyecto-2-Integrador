package com.example.plataforma_cerebritos.models.simulacros;

import java.time.LocalDateTime;

public class EvaluacionSimulacroDTO {
    private LocalDateTime fecha;
    private Double nota;
    private Integer pcorrectas;
    private Integer pincorrectas;
    private String cursoRendimientoPositivo;
    private String cursoRendimientoNegativo;

    public EvaluacionSimulacroDTO() {

    }

    public EvaluacionSimulacroDTO(LocalDateTime fecha, Double nota, Integer pcorrectas, Integer pincorrectas, String cursoRendimientoPositivo, String cursoRendimientoNegativo) {
        this.fecha = fecha;
        this.nota = nota;
        this.pcorrectas = pcorrectas;
        this.pincorrectas = pincorrectas;
        this.cursoRendimientoPositivo = cursoRendimientoPositivo;
        this.cursoRendimientoNegativo = cursoRendimientoNegativo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Integer getPcorrectas() {
        return pcorrectas;
    }

    public void setPcorrectas(Integer pcorrectas) {
        this.pcorrectas = pcorrectas;
    }

    public Integer getPincorrectas() {
        return pincorrectas;
    }

    public void setPincorrectas(Integer pincorrectas) {
        this.pincorrectas = pincorrectas;
    }

    public String getCursoRendimientoPositivo() {
        return cursoRendimientoPositivo;
    }

    public void setCursoRendimientoPositivo(String cursoRendimientoPositivo) {
        this.cursoRendimientoPositivo = cursoRendimientoPositivo;
    }

    public String getCursoRendimientoNegativo() {
        return cursoRendimientoNegativo;
    }

    public void setCursoRendimientoNegativo(String cursoRendimientoNegativo) {
        this.cursoRendimientoNegativo = cursoRendimientoNegativo;
    }
}
