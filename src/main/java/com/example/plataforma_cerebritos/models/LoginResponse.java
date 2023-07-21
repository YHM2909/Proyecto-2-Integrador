package com.example.plataforma_cerebritos.models;

public class LoginResponse {
    private String usuario;
    private String nombres;
    private Integer idAlumno;
    private Integer iduniversidad;

    public LoginResponse(String usuario, String nombres, Integer idAlumno, Integer iduniversidad) {
        this.usuario = usuario;
        this.nombres = nombres;
        this.idAlumno = idAlumno;
        this.iduniversidad = iduniversidad;
    }

    public String getUsuario() {
        return usuario;
    }

    public Integer getIduniversidad() {
        return iduniversidad;
    }

    public void setIduniversidad(Integer iduniversidad) {
        this.iduniversidad = iduniversidad;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Integer getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }
}
