package com.example.plataforma_cerebritos.controller;

import com.example.plataforma_cerebritos.models.*;
import com.example.plataforma_cerebritos.models.simulacros.EvaluacionSimulacroDTO;
import com.example.plataforma_cerebritos.models.simulacros.ResultadoCursoSimulacro;
import com.example.plataforma_cerebritos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.Integer.parseInt;

@Controller
public class SimulacrosController {
    @Autowired
    private CursoResultadoSimulacroRepository cursoResultadoSimulacroRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private EvaluacionSimulacroRepository evaluacionSimulacroRepository;
    @Autowired
    private PreguntaRepository preguntaRepository;
    @Autowired
    private RespuestaPreguntaRepository respuestaPreguntaRepository;
    @Autowired
    private CursoGrupoRepository cursoGrupoRepository;

    @Autowired
    private ResultadoPreguntaSimulacroRepository resultadoPreguntaSimulacroRepository;
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
    @GetMapping("/reportesimulacros")
    public String reportesimulacros(@RequestParam("idalumno") int idalumno, Model model) {
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

        model.addAttribute("evaluacionesDTO", evaluacionesDTO);
        model.addAttribute("idalumno", idalumno);
        return "reportesimulacros";
    }

    @GetMapping("/masdetallesresultados_simulacros")
    public String masdetallesresultados_simulacros(@RequestParam("idevaluacionsimulacro") int idevaluacionsimulacro, Model model) {
        List<ResultadoPreguntaSimulacro> resultados = resultadoPreguntaSimulacroRepository.findByidEvaluacionSimulacro(idevaluacionsimulacro);

        // Crear un mapa para agrupar los resultados por curso
        Map<Curso, int[]> resultadosPorCurso = new HashMap<>();

        // Procesar los resultados y contar los estados por curso
        for (ResultadoPreguntaSimulacro resultado : resultados) {
            int estado = resultado.getEstado();
            int[] contadorEstados = resultadosPorCurso.getOrDefault(resultado.getCurso(), new int[]{0, 0});

            if (estado == 0) {
                contadorEstados[0]++; // Aumentar contador de estado 0
            } else if (estado == 1) {
                contadorEstados[1]++; // Aumentar contador de estado 1
            }

            resultadosPorCurso.put(resultado.getCurso(), contadorEstados);
        }

        // Convertir el mapa a una lista de objetos
        List<ResultadoCursoSimulacro> resultadosCursoList = new ArrayList<>();
        for (Map.Entry<Curso, int[]> entry : resultadosPorCurso.entrySet()) {
            ResultadoCursoSimulacro resultadoCursosimulacro = new ResultadoCursoSimulacro();
            resultadoCursosimulacro.setCurso(entry.getKey());
            resultadoCursosimulacro.setEstado0(entry.getValue()[0]);
            resultadoCursosimulacro.setEstado1(entry.getValue()[1]);
            resultadosCursoList.add(resultadoCursosimulacro);
        }
        model.addAttribute("idevaluacionsimulacro", idevaluacionsimulacro);
        model.addAttribute("resultadospreguntasimulacro", resultados);
        model.addAttribute("resultadosPorCurso", resultadosCursoList);
        return "detallesresultadossimulacros";
    }

    @GetMapping("/datasimulacro_cursos")
    public ResponseEntity<List<ResultadoCursoSimulacro>> datasimulacro_cursos(@RequestParam("idevaluacionsimulacro") int idevaluacionsimulacro) {
        List<ResultadoPreguntaSimulacro> resultados = resultadoPreguntaSimulacroRepository.findByidEvaluacionSimulacro(idevaluacionsimulacro);
        // Crear un mapa para agrupar los resultados por curso
        Map<Curso, int[]> resultadosPorCurso = new HashMap<>();
        // Procesar los resultados y contar los estados por curso
        for (ResultadoPreguntaSimulacro resultado : resultados) {
            int estado = resultado.getEstado();
            int[] contadorEstados = resultadosPorCurso.getOrDefault(resultado.getCurso(), new int[]{0, 0});
            if (estado == 0) {
                contadorEstados[0]++; // Aumentar contador de estado 0
            } else if (estado == 1) {
                contadorEstados[1]++; // Aumentar contador de estado 1
            }
            resultadosPorCurso.put(resultado.getCurso(), contadorEstados);
        }
        // Convertir el mapa a una lista de objetos
        List<ResultadoCursoSimulacro> resultadosCursoList = new ArrayList<>();
        for (Map.Entry<Curso, int[]> entry : resultadosPorCurso.entrySet()) {
            ResultadoCursoSimulacro resultadoCursosimulacro = new ResultadoCursoSimulacro();
            resultadoCursosimulacro.setCurso(entry.getKey());
            resultadoCursosimulacro.setEstado0(entry.getValue()[0]);
            resultadoCursosimulacro.setEstado1(entry.getValue()[1]);
            resultadosCursoList.add(resultadoCursosimulacro);
        }
        return ResponseEntity.ok(resultadosCursoList);
    }

    @GetMapping("/simulacros/{idevaluacion}")
    public ResponseEntity<List<CursoResultadoSimulacro>> obtenerResultadosSimulacro(@PathVariable("idevaluacion") int idevaluacion) {
        List<CursoResultadoSimulacro> resultadosSimulacroCursos = cursoResultadoSimulacroRepository.findByIdEvaluacionSimulacro(idevaluacion);

        // Iterar sobre los resultados y obtener el nombre del curso
        for (CursoResultadoSimulacro resultado : resultadosSimulacroCursos) {
            int idCurso = resultado.getIdCurso();
            Curso curso = cursoRepository.findCursoByIdCurso(idCurso);

            // Establecer el nombre del curso en el resultado
            resultado.setNombreCurso(curso.getNombre());
        }

        System.out.println(resultadosSimulacroCursos.size());
        return ResponseEntity.ok(resultadosSimulacroCursos);
    }

    @GetMapping("/realizarsimulacro")
    public String realizarsimulacro(@RequestParam("idalumno") String idalumno,
                                    @RequestParam("iduniversidad") String idUniversidad, Model model) {
        // Convertir el idUniversidad de String a Integer
        Integer idUni = parseInt(idUniversidad);

        // Obtener la lista de CursoGrupo que corresponden a la idUniversidad
        List<CursoGrupo> cursoGrupos = cursoGrupoRepository.findAllByIdUniversidad(idUni);

        // Iterar sobre la lista de CursoGrupo y buscar las preguntas para cada idCurso
        for (CursoGrupo cursoGrupo : cursoGrupos) {
            int idCurso = cursoGrupo.getIdCurso();
            // Obtenemos la cantidad preguntas que le pertenecen a cada curso
            int cantidadPreguntas = cursoGrupo.getCantidadPreguntas();
            // Obtener todas las preguntas que le pertenecen al curso
            List<Pregunta> preguntas = preguntaRepository.findAllByIdCurso(idCurso);

            // Mezclar al azar el orden de las preguntas
            Collections.shuffle(preguntas);

            // Verificar si la cantidad de preguntas es mayor a la cantidad deseada
            if (preguntas.size() > cantidadPreguntas) {
                // Tomar solo la cantidad de preguntas deseada
                preguntas = preguntas.subList(0, cantidadPreguntas);
            }

            for (Pregunta pregunta : preguntas) {
                List<RespuestaPregunta> respuestas = respuestaPreguntaRepository.findByPreguntaId(pregunta.getId());
                pregunta.setRespuestas(respuestas);
            }

            // Obtener el curso correspondiente y establecerlo en cursoGrupo
            Curso curso = cursoRepository.findById(idCurso).orElse(null);
            cursoGrupo.setCurso(curso);
            // Asignar la lista de preguntas al atributo 'preguntas' de CursoGrupo
            cursoGrupo.setPreguntas(preguntas);
        }

        // Inicializar y crear una fila en evaluacionsimulacro
        EvaluacionSimulacro evaluacionSimulacro = new EvaluacionSimulacro();
        evaluacionSimulacro.setIdAlumno(parseInt(idalumno));
        evaluacionSimulacro.setNota(0.0); // Establece la nota inicial como 0.0
        evaluacionSimulacro.setFecha(null); // Establece la fecha actual
        evaluacionSimulacroRepository.save(evaluacionSimulacro);
        Integer idSimulacroEvaluacion = evaluacionSimulacro.getId();

        LocalTime horaActual = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss"); // Formato de hora:minuto:segundo
        String horaActualFormateada = horaActual.format(formatter);
        // Agregar la lista de CursoGrupo al modelo para ser usada en la vista
        model.addAttribute("cursoGrupos", cursoGrupos);
        model.addAttribute("idSimulacroEvaluacion", idSimulacroEvaluacion);
        model.addAttribute("tiempoExamen", 180);
        model.addAttribute("horaActual", horaActualFormateada);
        model.addAttribute("cantidadPreguntas", 100);
        return "realizarsimulacro";
    }

    @PostMapping("/recopilar_respuestas_simulacro")
    public ResponseEntity<Map<String, Object>> recopilar_respuestas_simulacro(@RequestBody RespuestasSimulacro respuestasSimulacro, Model model) {
        List<RespuestasSimulacro.Respuesta> respuestas = respuestasSimulacro.getListarespuestas();
        int idevaluacionsimulacro = respuestasSimulacro.getIdevaluacionsimulacro();
        System.out.println(idevaluacionsimulacro);
        int totalPreguntas = respuestas.size();
        double puntajeMaximo = 2000.0;
        double puntajePorPregunta = puntajeMaximo / totalPreguntas;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        puntajePorPregunta = Double.parseDouble(decimalFormat.format(puntajePorPregunta));
        int preguntasCorrectas = 0;

        for (RespuestasSimulacro.Respuesta respuesta : respuestas) {
            if (respuesta.getRespuestaValor() == 1) {
                preguntasCorrectas++;
            }
        }
        double nota = preguntasCorrectas * puntajePorPregunta;
        nota = Math.round(nota * 100.0) / 100.0;

        EvaluacionSimulacro evaluacionSimulacro = evaluacionSimulacroRepository.findById(idevaluacionsimulacro).orElse(null);
        if (evaluacionSimulacro != null) {
            evaluacionSimulacro.setNota(nota);
            evaluacionSimulacro.setFecha(LocalDateTime.now());

            evaluacionSimulacroRepository.save(evaluacionSimulacro);

            // Agrupar respuestas por curso
            Map<Integer, List<RespuestasSimulacro.Respuesta>> respuestasPorCurso = new HashMap<>();
            for (RespuestasSimulacro.Respuesta respuesta : respuestas) {
                int idCurso = respuesta.getIdcurso();
                respuestasPorCurso.computeIfAbsent(idCurso, k -> new ArrayList<>()).add(respuesta);


                // Almacenar estado de cada respuesta de la pregunta del simulacro
                ResultadoPreguntaSimulacro resultadoPreguntaSimulacro = new ResultadoPreguntaSimulacro();
                resultadoPreguntaSimulacro.setIdCursoResultado(respuesta.getIdcurso());
                resultadoPreguntaSimulacro.setIdPregunta(respuesta.getPreguntaId());
                resultadoPreguntaSimulacro.setIdEvaluacionSimulacro(idevaluacionsimulacro);
                if (respuesta.getRespuestaValor() == 1) {
                    resultadoPreguntaSimulacro.setCalificacion(puntajePorPregunta);
                }else{
                    resultadoPreguntaSimulacro.setCalificacion(0);
                }
                resultadoPreguntaSimulacro.setEstado(respuesta.getRespuestaValor());

                resultadoPreguntaSimulacroRepository.save(resultadoPreguntaSimulacro);
            }

            // Calcular preguntas correctas e incorrectas para cada curso y guardar en CursoResultadoSimulacro
            for (Map.Entry<Integer, List<RespuestasSimulacro.Respuesta>> entry : respuestasPorCurso.entrySet()) {
                int idCurso = entry.getKey();
                List<RespuestasSimulacro.Respuesta> respuestasCurso = entry.getValue();

                int preguntasCorrectasCurso = 0;
                for (RespuestasSimulacro.Respuesta respuestaCurso : respuestasCurso) {
                    if (respuestaCurso.getRespuestaValor() == 1) {
                        preguntasCorrectasCurso++;
                    }
                }
                int preguntasIncorrectasCurso = respuestasCurso.size() - preguntasCorrectasCurso;

                CursoResultadoSimulacro resultadoSimulacro = new CursoResultadoSimulacro(idCurso, idevaluacionsimulacro, preguntasCorrectasCurso, preguntasIncorrectasCurso);
                cursoResultadoSimulacroRepository.save(resultadoSimulacro);
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("idevaluacionsimulacro", idevaluacionsimulacro);
        return ResponseEntity.ok(response);
    }
}
