package com.example.plataforma_cerebritos.models;

import java.util.List;

public class DatosCursoExamen {
    private int idcurso;
    private int idevaluacion;
    private List<Respuesta> listarespuestas;

    public static class Respuesta {
        private int preguntaId;
        private int respuestaId;
        private int respuestaValor;

        // Agrega los getters y setters correspondientes

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
    public int getIdcurso() {
        return idcurso;
    }

    public void setIdcurso(int idcurso) {
        this.idcurso = idcurso;
    }

    public int getIdevaluacion() {
        return idevaluacion;
    }

    public void setIdevaluacion(int idevaluacion) {
        this.idevaluacion = idevaluacion;
    }

    public List<Respuesta> getListarespuestas() {
        return listarespuestas;
    }

    public void setListarespuestas(List<Respuesta> listarespuestas) {
        this.listarespuestas = listarespuestas;
    }
}
