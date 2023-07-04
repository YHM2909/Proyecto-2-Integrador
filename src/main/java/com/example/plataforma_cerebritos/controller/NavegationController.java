package com.example.plataforma_cerebritos.controller;
import com.example.plataforma_cerebritos.models.CursoGrupo;
import com.example.plataforma_cerebritos.models.Curso;
import com.example.plataforma_cerebritos.repository.CursoGrupoRepository;
import com.example.plataforma_cerebritos.repository.CursoRepository;
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
            // Haz algo con los valores de usuario y nombre
        return "dashboard";
    }
    @GetMapping("/cursos")
    public String cursos(@RequestParam("idalumno") String idalumno,
                         @RequestParam("iduniversidad") String iduniversidad,
                         @RequestParam("usuario") String usuario,
                         Model model) {
        // Obtener la lista de cursos del CursoGrupoRepository
        List<CursoGrupo> cursosGrupo = cursoGrupoRepository.findByUniversidadAndAlumno(Integer.parseInt(iduniversidad), Integer.parseInt(idalumno));
        // Obtener los nombres de los cursos del CursoRepository
        List<String> nombresCursos = cursosGrupo.stream()
                .map(cursoGrupo -> cursoRepository.findById(cursoGrupo.getIdCurso()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Curso::getNombre)
                .collect(Collectors.toList());
        model.addAttribute("cursos", nombresCursos);
        System.out.println("-----------NOMBRES DE CURSOS----------");
        System.out.println(nombresCursos);
        return "cursos";
    }
}
