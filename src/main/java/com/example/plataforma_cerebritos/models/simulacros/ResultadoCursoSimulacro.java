package com.example.plataforma_cerebritos.models.simulacros;

import com.example.plataforma_cerebritos.models.Curso;

public class ResultadoCursoSimulacro {
    private Curso curso;
    private int estado0;
    private int estado1;

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public int getEstado0() {
        return estado0;
    }

    public void setEstado0(int estado0) {
        this.estado0 = estado0;
    }

    public int getEstado1() {
        return estado1;
    }

    public void setEstado1(int estado1) {
        this.estado1 = estado1;
    }
}
