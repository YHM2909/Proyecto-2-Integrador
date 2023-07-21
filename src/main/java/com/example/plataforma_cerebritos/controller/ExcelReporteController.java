package com.example.plataforma_cerebritos.controller;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/excel")
public class ExcelReporteController {
    @GetMapping("/report")
    public void writeExcel(HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        CellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setBorderTop(BorderStyle.THIN);
        dataCellStyle.setBorderBottom(BorderStyle.THIN);
        dataCellStyle.setBorderLeft(BorderStyle.THIN);
        dataCellStyle.setBorderRight(BorderStyle.THIN);

        Map<String, Object[]> data = new HashMap<>();
        data.put("1", new Object[]{"Fecha", "Nota", "R. Blanco", "R. Correctas", "R. Incorrectas", "Curso(+)Rendimiento", "Curso(-)Rendimiento"});
        data.put("2", new Object[]{"2023-07-21 10:34:34", 100, 10, 76, 14, "Lenguaje", "Quimica"});
        data.put("3", new Object[]{"2023-07-18 22:25:09", 100, 6, 80, 14, "Fisica", "Aritmetica"});
        data.put("4", new Object[]{"2023-07-18 22:17:48", 100, 3, 82, 15, "Filosofia", "Fisica"});
        data.put("5", new Object[]{"2023-07-16 23:35:18", 100, 5, 89, 6, "Literatura", "Trigonometria"});
        data.put("6", new Object[]{"2023-07-16 23:30:02", 100, 5, 87, 8, "Algebra", "Historia Uniersal"});
        data.put("7", new Object[]{"2023-07-16 12:07:15", 100, 9, 80, 11, "Geometria", "Lenguaje"});

        int rowIndex = 0;
        for (Map.Entry<String, Object[]> entry : data.entrySet()) {
            String key = entry.getKey();
            Object[] rowData = entry.getValue();

            Row row = sheet.createRow(rowIndex);
            for (int i = 0; i < rowData.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(rowData[i].toString());

                if (rowIndex == 0) {
                    cell.setCellStyle(headerStyle);
                }
            }

            rowIndex++;
        }
        for (int i = 0; i < data.get("1").length; i++) {
            sheet.autoSizeColumn(i);
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=reportesimulacion.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
