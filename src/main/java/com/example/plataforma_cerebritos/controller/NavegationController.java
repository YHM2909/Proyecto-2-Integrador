package com.example.plataforma_cerebritos.controller;
import com.example.plataforma_cerebritos.models.*;
import com.example.plataforma_cerebritos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

@Controller
public class NavegationController {
    @Autowired
    private CursoGrupoRepository cursoGrupoRepository;
    @Autowired
    private EvaluacionSimulacroRepository evaluacionSimulacroRepository;
    @Autowired
    private EvaluacionCursoRepository evaluacionCursoRepository;
    @Autowired
    private TemarioRepository temarioRepository;
    @Autowired
    private ResultadoPreguntaCursoRepository resultadoPreguntaCursoRepository;

    @Autowired
    private ResultadoPreguntaSimulacroRepository resultadoPreguntaSimulacroRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @GetMapping("/")
    public String index(Model model) {
        return "dashboard";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "dashboard";
    }

    @GetMapping("/detallesimulacro")
    public String detallesimulacro(@RequestParam("idalumno") String idalumno,
                                   @RequestParam("iduniversidad") String idUniversidad, Model model) {
        List<EvaluacionSimulacro> evaluacionesSimulacro = evaluacionSimulacroRepository.findByidAlumno(Integer.parseInt(idalumno));
        // Limitar las evaluaciones a los 12 registros más recientes
        int startIndex = Math.max(evaluacionesSimulacro.size() - 12, 0);
        List<EvaluacionSimulacro> UltimasevaluacionesSimulacro = evaluacionesSimulacro.subList(startIndex, evaluacionesSimulacro.size());
        List<CursoDto> cursos = cursoGrupoRepository.findCursosByUniversidad(Integer.parseInt(idUniversidad));
        // Obtener los nombres de los cursos del CursoRepository
        model.addAttribute("cursos", cursos);
        model.addAttribute("idalumno", idalumno);
        model.addAttribute("evaluacionesSimulacro", UltimasevaluacionesSimulacro);
        return "detallesimulacro";
    }

    @GetMapping("/resultadoscurso")
    public String resultadoscurso(@RequestParam("idevaluacion") int idevaluacion, Model model) {
        System.out.println("ID de evaluación: " + idevaluacion);

        Optional<EvaluacionCurso> evaluacionOptional = evaluacionCursoRepository.findById(idevaluacion);
        if (evaluacionOptional.isPresent()) {
            EvaluacionCurso evaluacion = evaluacionOptional.get();
            double nota = evaluacion.getNota();
            LocalDateTime fecha = evaluacion.getFecha();

            List<ResultadoPreguntaCurso> resultados = resultadoPreguntaCursoRepository.findByIdEvaluacionCurso(idevaluacion);
            model.addAttribute("resultados", resultados);
            model.addAttribute("nota", nota);
            model.addAttribute("fecha", fecha);
        } else {
            // Manejar el caso en que la evaluación no exista
            // Por ejemplo, redireccionar a una página de error o mostrar un mensaje de error en la vista.
        }
        return "resultadoscurso";
    }

    @GetMapping("/cursos")
    public String cursos(@RequestParam("idalumno") String idalumno,
                         @RequestParam("iduniversidad") String idUniversidad, Model model) {
        // Obtener la lista de cursos del CursoGrupoRepository
        List<CursoDto> cursos = cursoGrupoRepository.findCursosByUniversidad(Integer.parseInt(idUniversidad));
        // Obtener los nombres de los cursos del CursoRepository
        model.addAttribute("cursos", cursos);
        model.addAttribute("idalumno", idalumno);
        return "cursos";
    }

    @GetMapping("/detallecurso")
    public String detallecurso(@RequestParam("idalumno") String idAlumno, @RequestParam("idcurso") String idCurso, Model model) {
        // Listar historial de examenes realizados, para ello declarar la entidad evaluacioncurso
        List<EvaluacionCurso> evaluaciones = evaluacionCursoRepository.findByidAlumnoAndIdCurso(Integer.parseInt(idAlumno), Integer.parseInt(idCurso));
        // Limitar las evaluaciones a los 12 registros más recientes
        int startIndex = Math.max(evaluaciones.size() - 12, 0);
        List<EvaluacionCurso> ultimasEvaluaciones = evaluaciones.subList(startIndex, evaluaciones.size());
        // Declarar entidad temario del curso y su repository
        List<Temario> temarios = temarioRepository.findByIdCurso(Integer.parseInt(idCurso));
        int idCursoInt = Integer.parseInt(idCurso);
        // Obtener los datos del curso, en este caso el nombre
        Curso curso = cursoRepository.findCursoByIdCurso(idCursoInt);
        String nombreCurso = curso.getNombre();
        // Enviamos todos esos datos mediante el model.addAttributte
        model.addAttribute("idalumno", idAlumno);
        model.addAttribute("idcurso", idCurso);
        model.addAttribute("temarios", temarios);
        model.addAttribute("evaluaciones", ultimasEvaluaciones);
        model.addAttribute("nombreCurso", nombreCurso);
        return "detallecurso";
    }

    @GetMapping("/resultados_simulacro")
    public String resultados_simulacro(@RequestParam("idevaluacionsimulacro") int idevaluacionsimulacro, Model model) {
        EvaluacionSimulacro evaluacionSimulacro = evaluacionSimulacroRepository.findById(idevaluacionsimulacro).orElse(null);
        List<ResultadoPreguntaSimulacro> resultados = resultadoPreguntaSimulacroRepository.findByidEvaluacionSimulacro(idevaluacionsimulacro);
        // Obtener fecha y hora actual
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Formato de fecha y hora
        String fechaHoraActualFormateada = fechaHoraActual.format(formatter);
        model.addAttribute("resultadospreguntasimulacro", resultados);
        model.addAttribute("notasimulacro", evaluacionSimulacro.getNota());
        model.addAttribute("idevaluacionsimulacro", idevaluacionsimulacro);
        model.addAttribute("fechaHoraActualFormateada", fechaHoraActualFormateada);
        return "resultados_simulacro";
    }
}
