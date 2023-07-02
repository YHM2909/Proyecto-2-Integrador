package com.example.plataforma_cerebritos.models;

public class Alumno {
    private int idAlumno;
    private int idUniversidad;
    private String password;
    private String nombres;
    private String dni;
    private String correo;
    private String residencia;
    private String usuario;

    // Constructor
    public Alumno(int idAlumno, int idUniversidad, String password, String nombres, String dni, String correo, String residencia, String usuario) {
        this.idAlumno = idAlumno;
        this.idUniversidad = idUniversidad;
        this.password = password;
        this.nombres = nombres;
        this.dni = dni;
        this.correo = correo;
        this.residencia = residencia;
        this.usuario = usuario;
    }

    // Getters and Setters
    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdUniversidad() {
        return idUniversidad;
    }

    public void setIdUniversidad(int idUniversidad) {
        this.idUniversidad = idUniversidad;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
