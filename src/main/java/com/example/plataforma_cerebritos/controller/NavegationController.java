package com.example.plataforma_cerebritos.controller;
import com.example.plataforma_cerebritos.models.CursoDto;
import com.example.plataforma_cerebritos.models.CursoGrupo;
import com.example.plataforma_cerebritos.models.Curso;
import com.example.plataforma_cerebritos.models.EvaluacionCurso;
import com.example.plataforma_cerebritos.models.Temario;
import com.example.plataforma_cerebritos.repository.CursoGrupoRepository;
import com.example.plataforma_cerebritos.repository.CursoRepository;
import com.example.plataforma_cerebritos.repository.EvaluacionCursoRepository;
import com.example.plataforma_cerebritos.repository.TemarioRepository;
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
