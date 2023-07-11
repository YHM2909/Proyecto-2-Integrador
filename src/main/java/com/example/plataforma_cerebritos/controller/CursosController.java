package com.example.plataforma_cerebritos.controller;

import com.example.plataforma_cerebritos.models.*;
import com.example.plataforma_cerebritos.repository.CursoRepository;
import com.example.plataforma_cerebritos.repository.PreguntaRepository;
import com.example.plataforma_cerebritos.repository.RespuestaPreguntaRepository;
import com.example.plataforma_cerebritos.repository.TemarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.format.DateTimeFormatter;
@Controller
public class CursosController {
    @Autowired
    private PreguntaRepository preguntaRepository;
    @Autowired
    private TemarioRepository temarioRepository;
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
                              @RequestParam("temariosSeleccionados") List<Integer> temariosSeleccionados, Model model) {
        // Aquí se almacenarán las preguntas seleccionadas con sus respuestas
        List<Pregunta> preguntasSeleccionadas = new ArrayList<>();
        Curso curso = cursoRepository.findCursoByIdCurso(cursoId);
        String nombreCurso = curso.getNombre();
        int tiempoExamen = temariosSeleccionados.size() * 5 * 2;
        int cantidadPreguntas = temariosSeleccionados.size() * 5;
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
        // Obtener la hora actual
        LocalTime horaActual = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss"); // Formato de hora:minuto:segundo
        String horaActualFormateada = horaActual.format(formatter);
        model.addAttribute("preguntasSeleccionadas", preguntasSeleccionadas);
        model.addAttribute("nombreCurso", nombreCurso);
        model.addAttribute("tiempoExamen", tiempoExamen);
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
}
