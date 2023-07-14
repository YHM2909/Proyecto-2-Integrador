package com.example.plataforma_cerebritos.controller;
import com.example.plataforma_cerebritos.models.*;
import com.example.plataforma_cerebritos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

@Controller
public class NavegationController {
    @Autowired
    private CursoGrupoRepository cursoGrupoRepository;
    @Autowired
    private EvaluacionCursoRepository evaluacionCursoRepository;
    @Autowired
    private TemarioRepository temarioRepository;
    @Autowired
    private ResultadoPreguntaCursoRepository resultadoPreguntaCursoRepository;
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

    @GetMapping("/resultadoscurso")
    public String resultadoscurso(@RequestParam("idevaluacion") int idevaluacion, Model model) {
        // Utiliza el ID de evaluación en tu lógica de negocio
        System.out.println("ID de evaluación: " + idevaluacion);
        // Obtén la lista de resultados de preguntas para el idevaluacion
        List<ResultadoPreguntaCurso> resultados = resultadoPreguntaCursoRepository.findByIdEvaluacionCurso(idevaluacion);
        // Agrega la lista de resultados a tu modelo
        model.addAttribute("resultados", resultados);
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
        List<EvaluacionCurso> evaluaciones = evaluacionCursoRepository.findByidAlumnoAndIdCurso(Integer.parseInt(idAlumno), Integer.parseInt(idCurso));
        List<Temario> temarios = temarioRepository.findByIdCurso(Integer.parseInt(idCurso));
        int idCursoInt = Integer.parseInt(idCurso);
        Curso curso = cursoRepository.findCursoByIdCurso(idCursoInt);
        String nombreCurso = curso.getNombre();
        model.addAttribute("idalumno", idAlumno);
        model.addAttribute("idcurso", idCurso);
        model.addAttribute("temarios", temarios);
        model.addAttribute("evaluaciones", evaluaciones);
        model.addAttribute("nombreCurso", nombreCurso);
        return "detallecurso";
    }

}
