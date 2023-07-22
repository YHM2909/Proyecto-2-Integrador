package com.example.plataforma_cerebritos.controller;
import com.example.plataforma_cerebritos.models.CursoResultadoSimulacro;
import com.example.plataforma_cerebritos.models.EvaluacionSimulacro;
import com.example.plataforma_cerebritos.models.simulacros.EvaluacionSimulacroDTO;
import com.example.plataforma_cerebritos.repository.CursoResultadoSimulacroRepository;
import com.example.plataforma_cerebritos.repository.EvaluacionSimulacroRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/excel")
public class ExcelReporteController {
    @Autowired
    private EvaluacionSimulacroRepository evaluacionSimulacroRepository;
    @Autowired
    private CursoResultadoSimulacroRepository cursoResultadoSimulacroRepository;
    @GetMapping("/report")
    public void writeExcel(HttpServletResponse response, @RequestParam("idalumno") int idalumno) throws IOException {
        // Obtener la lista de EvaluacionSimulacroDTO utilizando dataExportarSimulacros
        List<EvaluacionSimulacroDTO> evaluacionesDTO = dataExportarSimulacros(idalumno);

        // Crear el workbook y sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        // Crear estilos
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

        // Agregar el encabezado
        Row headerRow = sheet.createRow(0);
        String[] header = {"Fecha", "Nota", "R. Blanco", "R. Correctas", "R. Incorrectas", "Curso(+)Rendimiento", "Curso(-)Rendimiento"};
        for (int i = 0; i < header.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(header[i]);
            cell.setCellStyle(headerStyle);
        }

        // Agregar los datos a las filas
        int rowIndex = 1;
        for (EvaluacionSimulacroDTO evaluacionDTO : evaluacionesDTO) {
            Row row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(evaluacionDTO.getFecha().toString());
            row.createCell(1).setCellValue(evaluacionDTO.getNota());
            row.createCell(2).setCellValue(evaluacionDTO.getPcorrectas());
            row.createCell(3).setCellValue(evaluacionDTO.getPcorrectas());
            row.createCell(4).setCellValue(evaluacionDTO.getPincorrectas());
            row.createCell(5).setCellValue(evaluacionDTO.getCursoRendimientoPositivo());
            row.createCell(6).setCellValue(evaluacionDTO.getCursoRendimientoNegativo());
            rowIndex++;
        }

        // Ajustar tamaño de las columnas
        for (int i = 0; i < header.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Configurar la respuesta HTTP para la descarga del archivo
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=reportesimulacion.xlsx");

        // Escribir el workbook en la respuesta HTTP
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    public List<EvaluacionSimulacroDTO> dataExportarSimulacros(int idalumno){
        List<EvaluacionSimulacro> evaluacionesSimulacro = evaluacionSimulacroRepository.findByidAlumno(idalumno);
        List<EvaluacionSimulacroDTO> evaluacionesDTO = new ArrayList<>();

        for (EvaluacionSimulacro evaluacion : evaluacionesSimulacro) {
            int pcorrectas = 0;
            int pincorrectas = 0;
            List<CursoResultadoSimulacro> resultadosSimulacroCursos = cursoResultadoSimulacroRepository.findByIdEvaluacionSimulacro(evaluacion.getId());

            for (CursoResultadoSimulacro resultadoscursos : resultadosSimulacroCursos) {
                pcorrectas += resultadoscursos.getpCorrectas();
                pincorrectas += resultadoscursos.getpIncorrectas();
            }

            // Obtener los cursos con el máximo de preguntas correctas e incorrectas
            String maxCursoCorrectas = getMaxCurso(resultadosSimulacroCursos, true);
            String maxCursoIncorrectas = getMaxCurso(resultadosSimulacroCursos, false);

            EvaluacionSimulacroDTO evaluacionDTO = new EvaluacionSimulacroDTO();
            evaluacionDTO.setFecha(evaluacion.getFecha());
            evaluacionDTO.setNota(evaluacion.getNota());
            evaluacionDTO.setPcorrectas(pcorrectas);
            evaluacionDTO.setPincorrectas(pincorrectas);
            evaluacionDTO.setCursoRendimientoPositivo(maxCursoCorrectas);
            evaluacionDTO.setCursoRendimientoNegativo(maxCursoIncorrectas);
            evaluacionesDTO.add(evaluacionDTO);
        }
        return evaluacionesDTO;
    }

    private String getMaxCurso(List<CursoResultadoSimulacro> resultadosSimulacroCursos, boolean isCorrectas) {
        int maxPreguntas = isCorrectas ? 0 : Integer.MAX_VALUE;
        String maxCurso = null;

        for (CursoResultadoSimulacro resultadoscursos : resultadosSimulacroCursos) {
            int preguntas = isCorrectas ? resultadoscursos.getpCorrectas() : resultadoscursos.getpIncorrectas();
            if (preguntas > maxPreguntas) {
                maxPreguntas = preguntas;
                maxCurso = resultadoscursos.getCurso().getNombre();
            }
        }

        return maxCurso;
    }
}