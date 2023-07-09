package com.example.plataforma_cerebritos.controller;

import com.example.plataforma_cerebritos.models.CursoDto;
import com.example.plataforma_cerebritos.models.RespuestaPregunta;
import com.example.plataforma_cerebritos.models.Temario;
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
import com.example.plataforma_cerebritos.models.Pregunta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class CursosController {
    @Autowired
    private PreguntaRepository preguntaRepository;
    @Autowired
    private TemarioRepository temarioRepository;
    @Autowired
    private RespuestaPreguntaRepository respuestaPreguntaRepository;
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

        // Consultar preguntas para cada temario seleccionado
        for (Integer temarioId : temariosSeleccionados) {
            List<Pregunta> preguntasTemario = preguntaRepository.findByIdTemario(temarioId);
            if (!preguntasTemario.isEmpty()) {
                // Obtener 5 preguntas de manera aleatoria
                int totalPreguntas = preguntasTemario.size();
                int preguntasSeleccionadasCount = Math.min(5, totalPreguntas); // Limitar a 5 preguntas
                List<Integer> indicesAleatorios = generarIndicesAleatorios(totalPreguntas, preguntasSeleccionadasCount);
                for (Integer indice : indicesAleatorios) {
                    Pregunta pregunta = preguntasTemario.get(indice);
                    List<RespuestaPregunta> respuestas = respuestaPreguntaRepository.findByPreguntaId(pregunta.getId());
                    pregunta.setRespuestas(respuestas);
                    preguntasSeleccionadas.add(pregunta);
                }
            }
        }
        model.addAttribute("preguntasSeleccionadas", preguntasSeleccionadas);
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
