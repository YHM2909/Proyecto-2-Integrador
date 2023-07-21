package com.example.plataforma_cerebritos.models;

import java.util.List;

public class RespuestasSimulacro {
    private int idevaluacionsimulacro;
    private List<Respuesta> listarespuestas;

    public static class Respuesta {
        private int idcurso;
        private int preguntaId;
        private int respuestaId;
        private int respuestaValor;

        public int getIdcurso() {
            return idcurso;
        }

        public void setIdcurso(int idcurso) {
            this.idcurso = idcurso;
        }

        public int getPreguntaId() {
            return preguntaId;
        }

        public void setPreguntaId(int preguntaId) {
            this.preguntaId = preguntaId;
        }

        public int getRespuestaId() {
            return respuestaId;
        }

        public void setRespuestaId(int respuestaId) {
            this.respuestaId = respuestaId;
        }

        public int getRespuestaValor() {
            return respuestaValor;
        }

        public void setRespuestaValor(int respuestaValor) {
            this.respuestaValor = respuestaValor;
        }
    }

    public int getIdevaluacionsimulacro() {
        return idevaluacionsimulacro;
    }

    public void setIdevaluacionsimulacro(int idevaluacionsimulacro) {
        this.idevaluacionsimulacro = idevaluacionsimulacro;
    }

    public List<Respuesta> getListarespuestas() {
        return listarespuestas;
    }

    public void setListarespuestas(List<Respuesta> listarespuestas) {
        this.listarespuestas = listarespuestas;
    }
}
