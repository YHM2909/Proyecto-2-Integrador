package com.example.plataforma_cerebritos.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import java.util.Map;

//2da prueba de apache poi
@Component("tipo/ver.xlsx")
public class Controller extends AbstractXlsxView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=\"tipo_view.xlsx\"");
        Tipo tipo = (Tipo) model.get("tipo");
        Sheet sheet = workbook.createSheet("Tipo Spring");
    }
}
