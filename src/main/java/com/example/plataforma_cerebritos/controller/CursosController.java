package com.example.plataforma_cerebritos.controller;

import com.example.plataforma_cerebritos.models.*;
import com.example.plataforma_cerebritos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.time.format.DateTimeFormatter;
@Controller
public class CursosController {
    @Autowired
    private PreguntaRepository preguntaRepository;
    @Autowired
    private TemarioRepository temarioRepository;
    @Autowired
    private ResultadoPreguntaCursoRepository resultadoPreguntaCursoRepository;
    @Autowired
    private EvaluacionCursoRepository evaluacionCursoRepository;
    @Autowired
    private RespuestaPreguntaRepository respuestaPreguntaRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @GetMapping("/temarioscurso")
    @ResponseBody
    public ResponseEntity<List<Temario>> temarioscurso(@RequestParam("idcurso") String idCurso) {
        List<Temario> temarios = temarioRepository.findByIdCurso(Integer.parseInt(idCurso));
        return ResponseEntity.ok(temarios);
    }

    @PostMapping("/examencurso")
    public String examencurso(@RequestParam("cursoId") Integer cursoId,
                              @RequestParam("idAlumno") Integer idAlumno,
                              @RequestParam("temariosSeleccionados") List<Integer> temariosSeleccionados, Model model) {
        // Aquí se almacenarán las preguntas seleccionadas con sus respuestas
        List<Pregunta> preguntasSeleccionadas = new ArrayList<>();
        Curso curso = cursoRepository.findCursoByIdCurso(cursoId);
        // Crear una nueva instancia de EvaluacionCurso y establecer los valores
        EvaluacionCurso evaluacionCurso = new EvaluacionCurso();
        evaluacionCurso.setIdAlumno(idAlumno);
        evaluacionCurso.setIdCurso(cursoId);
        evaluacionCurso.setNota(0.0); // Establece la nota inicial como 0.0
        evaluacionCurso.setFecha(null); // Establece la fecha actual
        // Guardar la nueva instancia de EvaluacionCurso en la base de datos
        evaluacionCursoRepository.save(evaluacionCurso);
        // Obtener el ID de la fila evaluacion creada
        Integer evaluacionId = evaluacionCurso.getId();
        int idCurso = curso.getIdCurso();
        for (Integer temarioId : temariosSeleccionados) {
            List<Pregunta> preguntasTemario = preguntaRepository.findByIdTemario(temarioId);
            if (!preguntasTemario.isEmpty()) {
                int totalPreguntas = preguntasTemario.size();
                int preguntasSeleccionadasCount = Math.min(5, totalPreguntas);
                List<Integer> indicesAleatorios = generarIndicesAleatorios(totalPreguntas, preguntasSeleccionadasCount);
                for (Integer indice : indicesAleatorios) {
                    Pregunta pregunta = preguntasTemario.get(indice);
                    List<RespuestaPregunta> respuestas = respuestaPreguntaRepository.findByPreguntaId(pregunta.getId());
                    pregunta.setRespuestas(respuestas);
                    preguntasSeleccionadas.add(pregunta);
                }
            }
        }
        int tiempoExamen = preguntasSeleccionadas.size() * 2;
        int cantidadPreguntas = preguntasSeleccionadas.size();
        // Obtener la hora actual
        LocalTime horaActual = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss"); // Formato de hora:minuto:segundo
        String horaActualFormateada = horaActual.format(formatter);
        model.addAttribute("preguntasSeleccionadas", preguntasSeleccionadas);
        model.addAttribute("nombreCurso", cursoId);
        model.addAttribute("idCurso", idCurso);
        model.addAttribute("tiempoExamen", tiempoExamen);
        model.addAttribute("evaluacionId", evaluacionId);
        model.addAttribute("horaActual", horaActualFormateada);
        model.addAttribute("cantidadPreguntas", cantidadPreguntas);
        return "examencurso";
    }


    private List<Integer> generarIndicesAleatorios(int totalPreguntas, int preguntasSeleccionadasCount) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < totalPreguntas; i++) {
            indices.add(i);
        }
        // Mezclar los índices de manera aleatoria
        long seed = System.nanoTime();
        Random random = new Random(seed);
        for (int i = totalPreguntas - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = indices.get(i);
            indices.set(i, indices.get(j));
            indices.set(j, temp);
        }
        // Obtener los primeros índices seleccionados
        return indices.subList(0, preguntasSeleccionadasCount);
    }

    @PostMapping("/resultevaluacioncurso")
    public ResponseEntity<Map<String, Object>> resultevaluacioncurso(@RequestBody DatosCursoExamen datosCursoExamen, Model model) {
        System.out.println("LLEGO AL CONTROLLER DE RESULTADOS");
        System.out.println(datosCursoExamen.getIdcurso());
        System.out.println(datosCursoExamen.getIdevaluacion());
        List<DatosCursoExamen.Respuesta> respuestas = datosCursoExamen.getListarespuestas();
        int totalPreguntas = respuestas.size();
        double puntajeMaximo = 20.0;
        double puntajePorPregunta = puntajeMaximo / totalPreguntas;
        int preguntasCorrectas = 0;
        for (DatosCursoExamen.Respuesta respuesta : respuestas) {
            if (respuesta.getRespuestaValor() == 1) {
                preguntasCorrectas++;
            }
        }
        double nota = preguntasCorrectas * puntajePorPregunta;
        // Obtén la instancia de EvaluacionCurso correspondiente al id de evaluación
        EvaluacionCurso evaluacionCurso = evaluacionCursoRepository.findById(datosCursoExamen.getIdevaluacion()).orElse(null);
        if (evaluacionCurso != null) {
            // Actualiza los campos nota y fecha de la instancia
            evaluacionCurso.setNota(nota);
            evaluacionCurso.setFecha(LocalDateTime.now());

            // Guarda la instancia actualizada en la base de datos
            evaluacionCursoRepository.save(evaluacionCurso);
            // Inserta una fila en la tabla resultadopreguntacurso por cada resultado de pregunta
            for (DatosCursoExamen.Respuesta respuesta : respuestas) {
                ResultadoPreguntaCurso resultadoPreguntaCurso = new ResultadoPreguntaCurso();
                resultadoPreguntaCurso.setIdEvaluacionCurso(evaluacionCurso.getId());
                resultadoPreguntaCurso.setIdPregunta(respuesta.getPreguntaId());
                if (respuesta.getRespuestaValor() == 1) {
                    resultadoPreguntaCurso.setCalificacion(puntajePorPregunta);
                }else{
                    resultadoPreguntaCurso.setCalificacion(0);
                }
                resultadoPreguntaCurso.setEstado(respuesta.getRespuestaValor());
                resultadoPreguntaCursoRepository.save(resultadoPreguntaCurso);
            }
        }
        Map<String, Object> response = new HashMap<>();
        // Agregar el idevaluacion al JSON de respuesta
        response.put("idevaluacion", evaluacionCurso.getId());
        // Devolver el JSON en la respuesta
        return ResponseEntity.ok(response);
    }

}
