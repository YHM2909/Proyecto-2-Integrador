package com.example.plataforma_cerebritos.controller;

import com.example.plataforma_cerebritos.models.*;
import com.example.plataforma_cerebritos.models.dashboard.CursoInfo;
import com.example.plataforma_cerebritos.repository.CursoRepository;
import com.example.plataforma_cerebritos.repository.CursoResultadoSimulacroRepository;
import com.example.plataforma_cerebritos.repository.EvaluacionCursoRepository;
import com.example.plataforma_cerebritos.repository.EvaluacionSimulacroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private EvaluacionCursoRepository evaluacionCursoRepository;
    @Autowired
    private CursoResultadoSimulacroRepository cursoResultadoSimulacroRepository;
    @Autowired
    private EvaluacionSimulacroRepository evaluacionSimulacroRepository;
    @GetMapping("/datossimulacrosgraficos")
    @ResponseBody
    public ResponseEntity<Map<String, Double>> datossimulacrosgraficos(@RequestParam("idAlumno") Integer idAlumno) {
        List<EvaluacionSimulacro> evaluacionesSimulacro = evaluacionSimulacroRepository.findByidAlumno(idAlumno);
        Map<String, Integer> preguntasCorrectasPorCurso = new HashMap<>();
        Map<String, Integer> vecesPorCurso = new HashMap<>();

        // Recorrer las evaluaciones y resultados para acumular las preguntas correctas por curso
        for (EvaluacionSimulacro evaluacion : evaluacionesSimulacro) {
            List<CursoResultadoSimulacro> resultadosSimulacroCursos = cursoResultadoSimulacroRepository.findByIdEvaluacionSimulacro(evaluacion.getId());

            for (CursoResultadoSimulacro resultadoscursos : resultadosSimulacroCursos) {
                Curso curso = resultadoscursos.getCurso();
                if (curso != null) {
                    String nombreCurso = curso.getNombre();
                    int preguntasCorrectas = resultadoscursos.getpCorrectas();

                    preguntasCorrectasPorCurso.put(nombreCurso, preguntasCorrectasPorCurso.getOrDefault(nombreCurso, 0) + preguntasCorrectas);
                    vecesPorCurso.put(nombreCurso, vecesPorCurso.getOrDefault(nombreCurso, 0) + 1);
                }
            }
        }

        // Calcular el promedio para cada curso
        Map<String, Double> promedioPreguntasPorCurso = new HashMap<>();
        for (Map.Entry<String, Integer> entry : preguntasCorrectasPorCurso.entrySet()) {
            String nombreCurso = entry.getKey();
            int preguntasCorrectasTotal = entry.getValue();
            int veces = vecesPorCurso.get(nombreCurso);
            double promedio = (double) preguntasCorrectasTotal / veces;

            // Redondear el promedio a dos decimales
            DecimalFormat df = new DecimalFormat("#.##");
            promedio = Double.parseDouble(df.format(promedio));

            promedioPreguntasPorCurso.put(nombreCurso, promedio);
        }

        return ResponseEntity.ok(promedioPreguntasPorCurso);
    }

    @GetMapping("/cursosconmasexamenes")
    @ResponseBody
    public ResponseEntity<Map<Integer, CursoInfo>> cursosconmasexamenes(@RequestParam("idAlumno") Integer idAlumno) {
        List<EvaluacionCurso> evaluacionesCurso = evaluacionCursoRepository.findByidAlumno(idAlumno);
        Map<Integer, CursoInfo> evaluacionesPorCurso = new HashMap<>();

        // Contar las evaluaciones por curso
        for (EvaluacionCurso evaluacionCurso : evaluacionesCurso) {
            int idCurso = evaluacionCurso.getIdCurso();
            evaluacionesPorCurso.put(idCurso, evaluacionesPorCurso.getOrDefault(idCurso, new CursoInfo("", 0)));

            // Obtener el nombre del curso desde la entidad CursoResultadoSimulacro
            Curso curso = cursoRepository.findById(idCurso).orElse(null);
            if (curso != null) {
                evaluacionesPorCurso.get(idCurso).setNombre(curso.getNombre());
            }

            evaluacionesPorCurso.get(idCurso).setCantidadEvaluaciones(evaluacionesPorCurso.get(idCurso).getCantidadEvaluaciones() + 1);
        }

        // En este punto, el mapa "evaluacionesPorCurso" tiene el conteo de evaluaciones por cada curso (id_curso)
        // junto con el nombre del curso

        return ResponseEntity.ok(evaluacionesPorCurso);
    }
}
