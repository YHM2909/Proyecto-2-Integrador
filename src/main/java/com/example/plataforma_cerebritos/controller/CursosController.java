package com.example.plataforma_cerebritos.controller;

import com.example.plataforma_cerebritos.models.*;
import com.example.plataforma_cerebritos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.DecimalFormat;
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
        // Mediante el idcurso se buscara la lista de temarios que le pertenecen
        List<Temario> temarios = temarioRepository.findByIdCurso(Integer.parseInt(idCurso));
        return ResponseEntity.ok(temarios);
    }

    @PostMapping("/examencurso")
    public String examencurso(@RequestParam("cursoId") Integer cursoId,
                              @RequestParam("idAlumno") Integer idAlumno,
                              @RequestParam("temariosSeleccionados") List<Integer> temariosSeleccionados, Model model) {

        // Aquí se almacenarán las preguntas seleccionadas con sus respuestas
        List<Pregunta> preguntasSeleccionadas = new ArrayList<>();
        // Obteniendo los datos del curso
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
        // Teniamos una lista de idtemarios seleccionados
        for (Integer temarioId : temariosSeleccionados) {
            List<Pregunta> preguntasTemario = preguntaRepository.findByIdTemario(temarioId);
            if (!preguntasTemario.isEmpty()) {
                int totalPreguntas = preguntasTemario.size();
                int preguntasSeleccionadasCount = Math.min(5, totalPreguntas);
                // De esa lista total de preguntas obtenemos la cantidad de preguntas que se evaluaran de manera aleatoria
                List<Integer> indicesAleatorios = generarIndicesAleatorios(totalPreguntas, preguntasSeleccionadasCount);
                // Tenemos seleccionadas nuestras preguntas aleatorias
                for (Integer indice : indicesAleatorios) {
                    Pregunta pregunta = preguntasTemario.get(indice);
                    // Obtener por cada pregunta la lista de respuestas
                    List<RespuestaPregunta> respuestas = respuestaPreguntaRepository.findByPreguntaId(pregunta.getId());
                    pregunta.setRespuestas(respuestas);
                    preguntasSeleccionadas.add(pregunta);
                }
            }
        }
        // Calcular el tiempo estimado para el examen (2 minutos por pregunta)
        int tiempoExamen = preguntasSeleccionadas.size() * 2;
        int cantidadPreguntas = preguntasSeleccionadas.size();
        // Obtener la hora actual
        LocalTime horaActual = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss"); // Formato de hora:minuto:segundo
        String horaActualFormateada = horaActual.format(formatter);
        // Agregar los atributos al modelo para pasarlos a la vista
        model.addAttribute("preguntasSeleccionadas", preguntasSeleccionadas);
        model.addAttribute("nombreCurso", curso.getNombre());
        model.addAttribute("idCurso", idCurso);
        model.addAttribute("tiempoExamen", tiempoExamen);
        model.addAttribute("evaluacionId", evaluacionId);
        model.addAttribute("horaActual", horaActualFormateada);
        model.addAttribute("cantidadPreguntas", cantidadPreguntas);
        return "examencurso";
    }


    // Totalpreguntas que puede ser 10, preguntas seleccionadas que siempre son 5 pero
    private List<Integer> generarIndicesAleatorios(int totalPreguntas, int preguntasSeleccionadasCount) {
        List<Integer> indices = new ArrayList<>();
        // si se encuentran menos
        // preguntas se agarraran lo maximo que encuentre
        for (int i = 0; i < totalPreguntas; i++) {
            indices.add(i);
        }
        // Mezclar los índices de manera aleatoria
        long seed = System.nanoTime();
        Random random = new Random(seed);
        for (int i = totalPreguntas - 1; i > 0; i--) {
            // LLamando la funcion random que agarrara de manera aleatoria
            int j = random.nextInt(i + 1);
            int temp = indices.get(i);
            // Tambien se ordenara de manera aleatoria
            indices.set(i, indices.get(j));
            indices.set(j, temp);
        }
        // Obtener los primeros índices seleccionados
        return indices.subList(0, preguntasSeleccionadasCount);
    }

    @PostMapping("/resultevaluacioncurso")
    public ResponseEntity<Map<String, Object>> resultevaluacioncurso(@RequestBody DatosCursoExamen datosCursoExamen, Model model) {
        List<DatosCursoExamen.Respuesta> respuestas = datosCursoExamen.getListarespuestas();

        int totalPreguntas = respuestas.size();

        double puntajeMaximo = 20.0;

        double puntajePorPregunta = puntajeMaximo / totalPreguntas;

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        puntajePorPregunta = Double.parseDouble(decimalFormat.format(puntajePorPregunta));

        int preguntasCorrectas = 0;

        for (DatosCursoExamen.Respuesta respuesta : respuestas) {
            if (respuesta.getRespuestaValor() == 1) {
                preguntasCorrectas++;
            }
        }
        double nota = preguntasCorrectas * puntajePorPregunta;

        EvaluacionCurso evaluacionCurso = evaluacionCursoRepository.findById(datosCursoExamen.getIdevaluacion()).orElse(null);
        if (evaluacionCurso != null) {
            evaluacionCurso.setNota(nota);
            evaluacionCurso.setFecha(LocalDateTime.now());

            evaluacionCursoRepository.save(evaluacionCurso);
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
        response.put("idevaluacion", evaluacionCurso.getId());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/datospregunta")
    @ResponseBody
    public ResponseEntity<PreguntaDTO> datospregunta(@RequestParam("idpregunta") Integer idpregunta) {
        Pregunta pregunta = preguntaRepository.findById(idpregunta).orElse(null);
        if (pregunta != null) {
            PreguntaDTO preguntaDTO = new PreguntaDTO();
            preguntaDTO.setImagenRespuesta(pregunta.getImagenRespuesta());
            preguntaDTO.setDescripcion(pregunta.getDescripcion());
            return ResponseEntity.ok(preguntaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
