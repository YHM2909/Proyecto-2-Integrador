package com.example.plataforma_cerebritos.controller;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="tipos")
public class Tipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private Character estado;
}
