package com.example.plataforma_cerebritos.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class NavegationController{

    @Autowired
    private TipoService tipoService;


    @GetMapping("/excel")
    public void generarExcel (HttpServletResponse response) throws  Exception{
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue= "attachment; filename=tipos.xlsx";
        response.setHeader(headerKey,headerValue);
        tipoService.generarExcel(response);

    }
}
