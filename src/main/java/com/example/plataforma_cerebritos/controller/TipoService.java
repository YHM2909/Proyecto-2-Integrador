package com.example.plataforma_cerebritos.controller;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TipoService {
    @Autowired
    private TipoRepository tipoRepository;
    public void generarExcel(HttpServletResponse response ) throws Exception{
        List<Tipo> tipos= tipoRepository.findAll();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("tipo info");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(0).setCellValue("Nombre");
        row.createCell(0).setCellValue("estado");

        Integer dataRowindex= 1;
        for(Tipo tipo: tipos){
            HSSFRow dataRow= sheet.createRow(dataRowindex);
            dataRow.createCell(0).setCellValue(tipo.getId());
            dataRow.createCell(1).setCellValue(tipo.getNombre());
            dataRow.createCell(2).setCellValue(tipo.getEstado());
            dataRowindex++;
        }
        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        ops.close();

    }
}
