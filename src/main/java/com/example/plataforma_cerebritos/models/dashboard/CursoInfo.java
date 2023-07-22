package com.example.plataforma_cerebritos.models.dashboard;

public class CursoInfo {
    private String nombre;
    private int cantidadEvaluaciones;

    public CursoInfo(String nombre, int cantidadEvaluaciones) {
        this.nombre = nombre;
        this.cantidadEvaluaciones = cantidadEvaluaciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadEvaluaciones() {
        return cantidadEvaluaciones;
    }

    public void setCantidadEvaluaciones(int cantidadEvaluaciones) {
        this.cantidadEvaluaciones = cantidadEvaluaciones;
    }
}
