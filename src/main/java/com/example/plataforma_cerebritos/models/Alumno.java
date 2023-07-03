package com.example.plataforma_cerebritos.models;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "alumno")
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Añade esta anotación para generar el ID automáticamente
    @Column(name = "id_alumno")
    private int idAlumno;
    @Column(name = "id_universidad")
    private int idUniversidad;
    @Column(name = "password")
    private String password;
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "dni")
    private String dni;
    @Column(name = "correo")
    private String correo;
    @Column(name = "residencia")
    private String residencia;
    @Column(name = "usuario")
    private String usuario;

    // Constructor
    public Alumno() {
    }

    public Alumno(int idUniversidad, String password, String nombres, String dni, String correo, String residencia, String usuario) {
        this.idUniversidad = idUniversidad;
        this.password = password;
        this.nombres = nombres;
        this.dni = dni;
        this.correo = correo;
        this.residencia = residencia;
        this.usuario = usuario;
    }

    // Getters and Setters
    public int  getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int  idAlumno) {
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
